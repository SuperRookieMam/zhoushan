package com.sofmit.health.extention;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class EntityPathExtention<M> {
    private static final long serialVersionUID = 1390655718244486136L;
//    private static final Const CONST = new Const();
    private final Map<String, Object> map;
    private final Map<String, Class<?>> fmap;

    public EntityPathExtention(EntityPathBase<M> entityPathBase) {
        map = Const.fieldMap.get(entityPathBase);
        fmap = Const.fieldClass.get(entityPathBase);
    }

    public <A, E> ArrayPath<A, E> createArray(String property, Class<? super A> type) {
        return getField(property);
    }

    public BooleanPath createBoolean(String property) {
        return getField(property);
    }

    public <A extends Comparable> ComparablePath<A> createComparable(String property, Class<? super A> type) {
        return getField(property);
    }

    public <A extends Enum<A>> EnumPath<A> createEnum(String property, Class<A> type) {
        return getField(property);
    }

    public <A extends Comparable> DatePath<A> createDate(String property, Class<? super A> type) {
        return getField(property);
    }

    public <A extends Comparable> DateTimePath<A> createDateTime(String property, Class<? super A> type) {
        return getField(property);
    }

    public <A, Q extends SimpleExpression<? super A>> CollectionPath<A, Q> createCollection(String property,
            Class<? super A> type, Class<? super Q> queryType, PathInits inits) {
        return getField(property);
    }

    public <A, E extends SimpleExpression<? super A>> ListPath<A, E> createList(String property, Class<? super A> type,
            Class<? super E> queryType, PathInits inits) {
        return getField(property);
    }

    public <K, V, E extends SimpleExpression<? super V>> MapPath<K, V, E> createMap(String property,
            Class<? super K> key, Class<? super V> value, Class<? super E> queryType) {
        return getField(property);
    }

    public <A extends Number & Comparable<?>> NumberPath<A> createNumber(String property, Class<? super A> type) {
        return getField(property);
    }

    public <A, E extends SimpleExpression<? super A>> SetPath<A, E> createSet(String property, Class<? super A> type,
            Class<? super E> queryType, PathInits inits) {
        return getField(property);
    }

    public <A> SimplePath<A> createSimple(String property, Class<? super A> type) {
        return getField(property);
    }

    public StringPath createString(String property) {
        return getField(property);
    }

    public <A extends Comparable> TimePath<A> createTime(String property, Class<? super A> type) {
        return getField(property);
    }

    private <T> T getField(String property) {
        try {
            return (T) map.get(property);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPropertySimpleName(String property) {
        if (map.get(property) == null) {
            return "";
        }
        return map.get(property).getClass().getSimpleName();
    }

    public <T> Class<T> getPropertyClass(String property) {
        return (Class<T>) fmap.get(property);
    }

    public void setDateValue(String property, String value, BooleanBuilder query, DatePath datePath, String type) {
        try {
            if (StringUtils.isEmpty(value) || value.equals("\"\"") || value.equals("''")) {
                return;
            }
            Class<?> aClass = fmap.get(property);
            if (LocalDate.class.isAssignableFrom(aClass)) {
                LocalDate localDate = LocalDate.parse(value);
                if (type.equals("eq"))
                    query.and(datePath.eq(localDate));
                if (type.equals("lt")) {
                    query.and(datePath.lt(localDate));
                } else if (type.equals("goe")) {
                    query.and(datePath.goe(localDate));
                }
            } else if (Date.class.isAssignableFrom(aClass)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = simpleDateFormat.parse(value);
                if (type.equals("eq"))
                    query.and(datePath.eq(date));
                if (type.equals("lt")) {
                    query.and(datePath.lt(date));
                } else if (type.equals("goe")) {
                    query.and(datePath.goe(date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDateTimeValue(String property, String value, BooleanBuilder query, DateTimePath dateTimePath,
            String type) {
        try {
            if (StringUtils.isEmpty(value) || value.equals("\"\"") || value.equals("''")) {
                return;
            }
            Class<?> aClass = fmap.get(property);
            if (ZonedDateTime.class.isAssignableFrom(aClass)) {
                ZonedDateTime zonedDateTime = ZonedDateTime.parse(value).toInstant().atZone(ZoneOffset.of("+8"));
                if (type.equals("eq"))
                    query.and(dateTimePath.eq(zonedDateTime));
                if (type.equals("lt")) {
                    query.and(dateTimePath.lt(zonedDateTime));
                } else if (type.equals("goe")) {
                    query.and(dateTimePath.goe(zonedDateTime));
                }
            } else if (LocalDateTime.class.isAssignableFrom(aClass)) {
                LocalDateTime localDateTime = LocalDateTime.parse(value);
                if (type.equals("eq"))
                    query.and(dateTimePath.eq(localDateTime));
                if (type.equals("lt")) {
                    query.and(dateTimePath.lt(localDateTime));
                } else if (type.equals("goe")) {
                    query.and(dateTimePath.goe(localDateTime));
                }
            } else if (Date.class.isAssignableFrom(aClass)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(value);
                if (type.equals("eq"))
                    query.and(dateTimePath.eq(date));
                if (type.equals("lt")) {
                    query.and(dateTimePath.lt(date));
                } else if (type.equals("goe")) {
                    query.and(dateTimePath.goe(date));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNumrberValue(String property, String value, BooleanBuilder query, NumberPath numberPath,
            String type) {
        try {
            if (StringUtils.isEmpty(value) || value.equals("\"\"") || value.equals("''")) {
                return;
            }
            Class<?> aClass = fmap.get(property);
            if (Integer.class.isAssignableFrom(aClass) || int.class.isAssignableFrom(aClass)) {
                if (type.equals("eq"))
                    query.and(numberPath.eq(Integer.valueOf(value)));
                if (type.equals("lt")) {
                    query.and(numberPath.lt(Integer.valueOf(value)));
                } else if (type.equals("goe")) {
                    query.and(numberPath.goe(Integer.valueOf(value)));
                }
            } else if (Long.class.isAssignableFrom(aClass) || long.class.isAssignableFrom(aClass)) {
                if (type.equals("eq"))
                    query.and(numberPath.eq(Long.valueOf(value)));
                if (type.equals("lt")) {
                    query.and(numberPath.lt(Long.valueOf(value)));
                } else if (type.equals("goe")) {
                    query.and(numberPath.goe(Long.valueOf(value)));
                }
            } else if (Double.class.isAssignableFrom(aClass) || double.class.isAssignableFrom(aClass)) {
                if (type.equals("eq"))
                    query.and(numberPath.eq(Double.valueOf(value)));
                if (type.equals("lt")) {
                    query.and(numberPath.lt(Double.valueOf(value)));
                } else if (type.equals("goe")) {
                    query.and(numberPath.goe(Double.valueOf(value)));
                }
            } else if (Float.class.isAssignableFrom(aClass) || float.class.isAssignableFrom(aClass)) {
                if (type.equals("eq"))
                    query.and(numberPath.eq(Float.valueOf(value)));
                if (type.equals("lt")) {
                    query.and(numberPath.lt(Float.valueOf(value)));
                } else if (type.equals("goe")) {
                    query.and(numberPath.goe(Float.valueOf(value)));
                }
            } else if (BigDecimal.class.isAssignableFrom(aClass)) {
                if (type.equals("eq"))
                    query.and(numberPath.eq(BigDecimal.valueOf(Double.valueOf(value))));
                if (type.equals("lt")) {
                    query.and(numberPath.lt(BigDecimal.valueOf(Double.valueOf(value))));
                } else if (type.equals("goe")) {
                    query.and(numberPath.goe(BigDecimal.valueOf(Double.valueOf(value))));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
