package com.sofmit.health.repository;

import com.sofmit.health.entity.CommunityHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommunityHospitalRepository
        extends JpaRepository<CommunityHospital, Long>, QuerydslPredicateExecutor<CommunityHospital> {

    public Optional<CommunityHospital> findTopByOrderByIdDesc();

    @Query(nativeQuery=true,value = "SELECT " +
                                        "t.* " +
                                    "FROM " +
                                        "(" +
                                            "SELECT " +
                                            "ch.id_ AS `id`, " +
                                            "ch.name_ AS `name`, " +
                                            "ch.tel_ AS `tel`, " +
                                            "ch.longitude_ AS `longitude`, " +
                                            "ch.latitude_ AS `latitude`, " +
                                            "ch.address_ AS `address`, " +
                                            "ch.service_time AS `serviceTime`, " +
                                            "ch.vaccines_name_ AS `vaccinesName`, " +
                                            "ch.state_ AS `state`, " +
                                            "'communityHospital' AS type " +
                                    "FROM " +
                                            "zs_community_hospital_ AS ch " +
                                    "UNION " +
                                    "SELECT " +
                                            "cdc.id_ AS `id`, " +
                                            "cdc.name_ AS `name`, " +
                                            "cdc.tel_ AS `tel`, " +
                                            "cdc.longitude_ AS `longitude`, " +
                                            "cdc.latitude_ AS `latitude`, " +
                                            "cdc.address_ AS `address`, " +
                                            "cdc.working_time_ AS `serviceTime`, " +
                                            "cdc.vaccines_name_ AS `vaccinesName`, " +
                                            "cdc.state_ AS `state`, " +
                                            "'cdc' AS type " +
                                    "FROM " +
                                            "zs_cdc_ AS cdc " +
                                                            ") AS t " +
                                    "WHERE " +
                                    "t.vaccinesName LIKE :vaccines " +
                                    "and t.`name` like :names "+
                                    "and t.`state` = '已发布'"+
                                    "ORDER BY " +
                                    "POWER(t.latitude - :latitude,2) + POWER(t.longitude - :longitude,2) limit :offset,:sizes")
    List<Map<String,Object>> find(@Param("vaccines") String vaccinesName,
                                  @Param("latitude") double latitude,
                                  @Param("longitude") double longitude,
                                  @Param("offset") long offset,
                                  @Param("sizes")long size,
                                  @Param("names") String name );

    @Query(nativeQuery=true,value = "SELECT " +
            "COUNT(*) " +
            "FROM " +
            "(" +
            "SELECT " +
            "ch.id_ AS `id`, " +
            "ch.name_ AS `name`, " +
            "ch.tel_ AS `tel`, " +
            "ch.longitude_ AS `longitude`, " +
            "ch.latitude_ AS `latitude`, " +
            "ch.address_ AS `address`, " +
            "ch.service_time AS `serviceTime`, " +
            "ch.vaccines_name_ AS `vaccinesName`, " +
            "ch.state_ AS `state`, " +
            "'communityHospital' AS type " +
            "FROM " +
            "zs_community_hospital_ AS ch " +
            "UNION " +
            "SELECT " +
            "cdc.id_ AS `id`, " +
            "cdc.name_ AS `name`, " +
            "cdc.tel_ AS `tel`, " +
            "cdc.longitude_ AS `longitude`, " +
            "cdc.latitude_ AS `latitude`, " +
            "cdc.address_ AS `address`, " +
            "cdc.working_time_ AS `serviceTime`, " +
            "cdc.vaccines_name_ AS `vaccinesName`, " +
            "cdc.state_ AS `state`, " +
            "'cdc' AS type " +
            "FROM " +
            "zs_cdc_ AS cdc " +
            ") AS t " +
            "WHERE " +
            "t.vaccinesName LIKE :vaccines "+
            "and t.`name` like :names "+
            "and t.`state` = '已发布'")
    Long findcount(@Param("vaccines") String vaccinesNam,@Param("names") String name);
}
