package com.sofmit.health.repository.impl;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.*;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.SQLQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

//import static com.querydsl.core.Target.*;
public class VaccineRepositoryImpl {

//    @Autowired
//    private JPAQueryFactory jqf;

    private SQLQueryFactory sqf;

    @Autowired
    public void setDatasource(DataSource datasource) {
        this.sqf = new SQLQueryFactory(new Configuration(new MySQLTemplates()), datasource);
    }

//    private final QCdc qCdc = QCdc.cdc;
//    private final QCommunityHospital qCh = QCommunityHospital.communityHospital;
//    private final QVaccine qVaccine = QVaccine.vaccine;

    @SuppressWarnings("unchecked")
    public List<?> findEByName() {
        StringPath namePath = Expressions.stringPath("name_");
        NumberPath<Double> latitudePath = Expressions.numberPath(Double.class, "latitude_");
        NumberPath<Double> longitudePath = Expressions.numberPath(Double.class, "longitude_");
        NumberExpression<Double> defaultLatitudePath = Expressions.asNumber(latitudePath.coalesce(0d)).subtract(30);
        NumberExpression<Double> defaultLongitudePath = Expressions.asNumber(longitudePath.coalesce(0d)).subtract(30);
        NumberExpression<Double> aPath = defaultLatitudePath.multiply(defaultLatitudePath)
                .add(defaultLongitudePath.multiply(defaultLongitudePath));
        StringExpression typePath = Expressions.asString("t1").as("type");
        StringExpression type2Path = Expressions.asString("t2").as("type");
        StringExpression cdcPath = Expressions.stringPath("zs_cdc_");
        StringExpression chPath = Expressions.stringPath("zs_community_hospital_");
        SubQueryExpression<Tuple> s1 = sqf
                .select(namePath, longitudePath, latitudePath, typePath)
                .from(cdcPath);
        SubQueryExpression<Tuple> s2 = sqf
                .select(namePath, longitudePath, latitudePath, type2Path)
                .from(chPath);//
        Expression<Tuple> unionPath = new SQLQuery<>().union(s1, s2).as("t1");

        SQLQuery<String> query = sqf.select(namePath).from(unionPath).orderBy(aPath.desc());

        query.fetch();
//        query()
//        Expressions.
//       SubQueryExpression<Tuple> t1=
//               jqf.query()
        return null;
    }
}
