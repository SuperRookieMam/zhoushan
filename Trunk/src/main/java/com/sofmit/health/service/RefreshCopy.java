package com.sofmit.health.service;

import com.dm.common.converter.AbstractConverter;
import com.dm.common.dto.RangePage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sofmit.health.dto.AbstractDto;
import com.sofmit.health.extention.EntityPathExtention;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

public interface RefreshCopy {
    static final String DRAW = "draw";
    static final String MAXID = "maxId";
    static final String PAGE = "page";
    static final String SIZE = "size";
    static final String STATE = "state";
    static final String YFB = "已发布";
    static final String SLAT = "slat";
    static final String SLON = "slon";
    static final String LONGITUDE = "longitude";
    static final String LATITUDE = "latitude";

    default <M, D extends AbstractDto<ID>, ID extends Serializable> List<M> iterableCopy(
            AbstractConverter<M, D> converter,
            JpaRepository<M, ID> repository,
            Class<M> model,
            Collection<D> dtos) {
        if (ObjectUtils.isEmpty(dtos)) {
            return null;
        } else {
            List<M> list = new ArrayList<>();
            for (D ele : dtos) {
                if (ObjectUtils.isEmpty(ele.getId())) {
                    try {
                        list.add(converter.copyProperties(model.newInstance(), ele));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    list.add(converter.copyProperties(repository.getOne(ele.getId()), ele));
                }
            }
            return list;
        }
    }

    default <M, D extends AbstractDto<ID>, ID extends Serializable> M oneCopy(AbstractConverter<M, D> converter,
            JpaRepository<M, ID> repository,
            Class<M> model,
            D dto) {
        if (ObjectUtils.isEmpty(dto)) {
            return null;
        }
        if (ObjectUtils.isEmpty(dto.getId())) {
            try {
                return converter.copyProperties(model.newInstance(), dto);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return converter.copyProperties(repository.getOne(dto.getId()), dto);
        }
    }

    /**
     * 根据条件查询
     *
     * @param map                       实体简单字段
     * @param pageable                  分页信息
     * @param entityPathBase            实体类型
     * @param querydslPredicateExecutor 对应的repostry
     */
    default <M> RangePage<M> findByMapPage(Map<String, String> map,
            Pageable pageable,
            EntityPathBase<M> entityPathBase,
            QuerydslPredicateExecutor<M> querydslPredicateExecutor) {
        EntityPathExtention<M> mEntity = new EntityPathExtention(entityPathBase);
        long maxToSet = Long.MIN_VALUE;
        BooleanBuilder query = new BooleanBuilder();
        try {
            if (!Objects.isNull(map.get(MAXID))) {
                maxToSet = Long.valueOf(map.get(MAXID));
            } else {
                Pageable pageable1 = PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")));
                Page<M> result = querydslPredicateExecutor.findAll(new BooleanBuilder(), pageable1);
                if (result.getContent().size() == 1) {
                    Object object = result.getContent().get(0);
                    Method method = object.getClass().getMethod("getId");
                    maxToSet = (Long) method.invoke(object);
                }
            }
            query.and(mEntity.createNumber("id", Long.class).loe(maxToSet));
            map.remove(MAXID);
            map.remove(PAGE);
            map.remove(SIZE);
            map.remove(DRAW);
            if (!"".equals(mEntity.getPropertySimpleName(STATE)) && !map.keySet().contains(STATE)) {
                query.and(mEntity.createString(STATE).eq(YFB));
            } else if (!"".equals(mEntity.getPropertySimpleName(STATE)) && map.keySet().contains(STATE)) {
                if (!"".equals(map.get(STATE))) {
                    query.and(mEntity.createString(STATE).eq(map.get(STATE)));
                    map.remove(STATE);
                }
            }
            // 根据map
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = map.get(key);
                switch (mEntity.getPropertySimpleName(key)) {
                case "StringPath":
                    if (!StringUtils.isEmpty(value))
                        query.and(mEntity.createString(key).containsIgnoreCase(value));
                    break;
                case "BooleanPath":
                    query.and(mEntity.createBoolean(key).eq(Boolean.valueOf(value)));
                    break;
                case "EnumPath":
                    query.and(mEntity.createEnum(key, mEntity.<Enum>getPropertyClass(key)).stringValue().eq(value));
                    break;
                case "NumberPath":
                    NumberPath numberPath = mEntity.createNumber(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setNumrberValue(key, values[0], query, numberPath, "goe");
                        mEntity.setNumrberValue(key, values[1], query, numberPath, "lt");
                    } else {
                        mEntity.setNumrberValue(key, value, query, numberPath, "eq");
                    }
                    break;
                case "DatePath":
                    DatePath datePath = mEntity.createDate(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateValue(key, values[0], query, datePath, "goe");
                        mEntity.setDateValue(key, values[1], query, datePath, "lt");
                    } else {
                        mEntity.setDateValue(key, value, query, datePath, "eq");
                    }
                    break;
                case "DateTimePath":
                    DateTimePath dateTimePath = mEntity.createDateTime(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateTimeValue(key, values[0], query, dateTimePath, "goe");
                        mEntity.setDateTimeValue(key, values[1], query, dateTimePath, "lt");
                    } else {
                        mEntity.setDateTimeValue(key, value, query, dateTimePath, "eq");
                    }
                    break;
                case "":
                    break;
                default:
                    throw new RuntimeException("暂不支持.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Page<M> result = querydslPredicateExecutor.findAll(query, pageable);
        return RangePage.of(maxToSet, result);
    }

    default <M> Iterable<M> findByMap(Map<String, String> map,
            EntityPathBase<M> entityPathBase,
            QuerydslPredicateExecutor<M> querydslPredicateExecutor) {
        EntityPathExtention<M> mEntity = new EntityPathExtention(entityPathBase);
        BooleanBuilder query = new BooleanBuilder();
        map.remove(MAXID);
        map.remove(PAGE);
        map.remove(SIZE);
        map.remove(DRAW);
        // 根据map
        Iterator<String> iterator = map.keySet().iterator();
        if (!"".equals(mEntity.getPropertySimpleName(STATE)) && !map.keySet().contains(STATE)) {
            query.and(mEntity.createString(STATE).eq(YFB));
        } else if (!"".equals(mEntity.getPropertySimpleName(STATE)) && map.keySet().contains(STATE)) {
            if (!"".equals(map.get(STATE))) {
                query.and(mEntity.createString(STATE).eq(map.get(STATE)));
                map.remove(STATE);
            }
        }
        try {
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = map.get(key);
                switch (mEntity.getPropertySimpleName(key)) {
                case "StringPath":
                    if (!StringUtils.isEmpty(value))
                        query.and(mEntity.createString(key).containsIgnoreCase(value));
                    break;
                case "BooleanPath":
                    query.and(mEntity.createBoolean(key).eq(Boolean.valueOf(value)));
                    break;
                case "EnumPath":
                    query.and(mEntity.createEnum(key, mEntity.<Enum>getPropertyClass(key)).stringValue().eq(value));
                    break;
                case "NumberPath":
                    NumberPath numberPath = mEntity.createNumber(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setNumrberValue(key, values[0], query, numberPath, "goe");
                        mEntity.setNumrberValue(key, values[1], query, numberPath, "lt");
                    } else {
                        mEntity.setNumrberValue(key, value, query, numberPath, "eq");
                    }
                    break;
                case "DatePath":
                    DatePath datePath = mEntity.createDate(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateValue(key, values[0], query, datePath, "goe");
                        mEntity.setDateValue(key, values[1], query, datePath, "lt");
                    } else {
                        mEntity.setDateValue(key, value, query, datePath, "eq");
                    }
                    break;
                case "DateTimePath":
                    DateTimePath dateTimePath = mEntity.createDateTime(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateTimeValue(key, values[0], query, dateTimePath, "goe");
                        mEntity.setDateTimeValue(key, values[1], query, dateTimePath, "lt");
                    } else {
                        mEntity.setDateTimeValue(key, value, query, dateTimePath, "eq");
                    }
                    break;
                case "":
                    break;
                default:
                    throw new RuntimeException("暂不支持.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return querydslPredicateExecutor.findAll(query);
    }

    default <M> RangePage<M> findByMapPage(Map<String, String> map,
            Pageable pageable,
            EntityPathBase<M> entityPathBase,
            JPAQueryFactory jpaQueryFactory) {
        EntityPathExtention<M> mEntity = new EntityPathExtention(entityPathBase);
        long maxToSet = Long.MIN_VALUE;
        BooleanBuilder query = new BooleanBuilder();
        try {
            if (!Objects.isNull(map.get(MAXID))) {
                maxToSet = Long.valueOf(map.get(MAXID));
            } else {
                List<M> list = jpaQueryFactory.select(entityPathBase).from(entityPathBase).offset(0).limit(1)
                        .orderBy(mEntity.createNumber("id", mEntity.getPropertyClass("id")).desc()).fetch();
                if (list.size() == 1) {
                    Object object = list.get(0);
                    Method method = object.getClass().getMethod("getId");
                    maxToSet = (Long) method.invoke(object);
                }
            }
            query.and(mEntity.createNumber("id", Long.class).loe(maxToSet));
            map.remove(MAXID);
            map.remove(DRAW);

            if (!"".equals(mEntity.getPropertySimpleName(STATE)) && !map.keySet().contains(STATE)) {
                query.and(mEntity.createString(STATE).eq(YFB));
            } else if (!"".equals(mEntity.getPropertySimpleName(STATE)) && map.keySet().contains(STATE)) {
                if (!"".equals(map.get(STATE))) {
                    query.and(mEntity.createString(STATE).eq(map.get(STATE)));
                    map.remove(STATE);
                }
            }

            // 根据map
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String value = map.get(key);
                switch (mEntity.getPropertySimpleName(key)) {
                case "StringPath":
                    if (!StringUtils.isEmpty(value))
                        query.and(mEntity.createString(key).containsIgnoreCase(value));
                    break;
                case "BooleanPath":
                    query.and(mEntity.createBoolean(key).eq(Boolean.valueOf(value)));
                    break;
                case "EnumPath":
                    query.and(mEntity.createEnum(key, mEntity.<Enum>getPropertyClass(key)).stringValue().eq(value));
                    break;
                case "NumberPath":
                    NumberPath numberPath = mEntity.createNumber(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setNumrberValue(key, values[0], query, numberPath, "goe");
                        mEntity.setNumrberValue(key, values[1], query, numberPath, "lt");
                    } else {
                        mEntity.setNumrberValue(key, value, query, numberPath, "eq");
                    }
                    break;
                case "DatePath":
                    DatePath datePath = mEntity.createDate(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateValue(key, values[0], query, datePath, "goe");
                        mEntity.setDateValue(key, values[1], query, datePath, "lt");
                    } else {
                        mEntity.setDateValue(key, value, query, datePath, "eq");
                    }
                    break;
                case "DateTimePath":
                    DateTimePath dateTimePath = mEntity.createDateTime(key, mEntity.getPropertyClass(key));
                    if (value.contains(",")) {
                        String[] values = value.split(",");
                        mEntity.setDateTimeValue(key, values[0], query, dateTimePath, "goe");
                        mEntity.setDateTimeValue(key, values[1], query, dateTimePath, "lt");
                    } else {
                        mEntity.setDateTimeValue(key, value, query, dateTimePath, "eq");
                    }
                    break;
                case "":
                    break;
                default:
                    throw new RuntimeException("暂不支持.....");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

//        Page<M> result=null;
        long page = map.get(PAGE) == null ? 0 : Long.valueOf(map.get(PAGE));
        long size = map.get(SIZE) == null ? 9999 : Long.valueOf(map.get(SIZE));
        JPAQuery<M> jpaQuery = jpaQueryFactory.select(entityPathBase).from(entityPathBase).where(query)
                .offset(page * size).limit(size);
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (map.containsKey(SLAT) && map.containsKey(SLON)) {
            if (!"".equals(map.get(SLAT)) && !"".equals(map.get(SLON))) {
                NumberPath<Double> latPath = mEntity.createNumber(LATITUDE, mEntity.<Double>getPropertyClass(LATITUDE));
                NumberPath<Double> lonPath = mEntity.createNumber(LONGITUDE,
                        mEntity.<Double>getPropertyClass(LONGITUDE));
                if (!"".equals(mEntity.getPropertySimpleName(LATITUDE))
                        && !"".equals(mEntity.getPropertySimpleName(LONGITUDE))) {
//                    List<OrderSpecifier> list = new ArrayList<>();
                    NumberExpression latExpression = latPath.subtract(Double.valueOf(map.get(SLAT)));
                    NumberExpression lonExpression = lonPath.subtract(Double.valueOf(map.get(SLON)));
                    NumberExpression lastSe = latExpression.multiply(latExpression)
                            .add(lonExpression.multiply(lonExpression));
                    orderSpecifiers.add(lastSe.asc());
                }
            }
        }
        pageable.getSort().get().forEach(ele -> {
            if ("NumberPath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {

                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(
                            mEntity.createNumber(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).asc());
                } else {
                    orderSpecifiers.add(mEntity
                            .createNumber(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).desc());
                }

            } else if ("StringPath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {
                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(mEntity.createString(ele.getProperty()).asc());
                } else {
                    orderSpecifiers.add(mEntity.createString(ele.getProperty()).desc());
                }
            } else if ("BooleanPath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {
                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(mEntity.createBoolean(ele.getProperty()).asc());
                } else {
                    orderSpecifiers.add(mEntity.createBoolean(ele.getProperty()).desc());
                }
            } else if ("DateTimePath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {
                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(mEntity
                            .createDateTime(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).asc());
                } else {
                    orderSpecifiers.add(mEntity
                            .createDateTime(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).desc());
                }
            } else if ("DatePath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {
                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(
                            mEntity.createDate(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).asc());
                } else {
                    orderSpecifiers.add(
                            mEntity.createDate(ele.getProperty(), mEntity.getPropertyClass(ele.getProperty())).desc());
                }
            } else if ("EnumPath".equals(mEntity.getPropertySimpleName(ele.getProperty()))) {
                if (ele.getDirection().compareTo(Sort.Direction.ASC) == 0) {
                    orderSpecifiers.add(mEntity
                            .createEnum(ele.getProperty(), mEntity.<Enum>getPropertyClass(ele.getProperty())).asc());
                } else {
                    orderSpecifiers.add(mEntity
                            .createEnum(ele.getProperty(), mEntity.<Enum>getPropertyClass(ele.getProperty())).desc());
                }
            }
        });
        if (!orderSpecifiers.isEmpty()) {
            jpaQuery = jpaQuery.orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]));
        }
        List<M> list1 = jpaQuery.fetch();
        long total = jpaQueryFactory.select(entityPathBase).from(entityPathBase).where(query).fetchCount();
        return RangePage.of(maxToSet, new PageImpl<>(list1, pageable, total));
    }

}
