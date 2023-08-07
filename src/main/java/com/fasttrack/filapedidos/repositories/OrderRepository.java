package com.fasttrack.filapedidos.repositories;

import com.fasttrack.filapedidos.models.OrderModel;
import com.fasttrack.filapedidos.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, UUID> {
    @Query(value = "SELECT COUNT(*) FROM PEDIDOS WHERE CD_STATUS = ?1", nativeQuery = true)
    Integer countByCdStatus(String number);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE PEDIDOS SET CD_STATUS = '4', DT_ATUALIZACAO = NOW() WHERE CD_STATUS = '3' AND DT_ATUALIZACAO < NOW() - INTERVAL '2 minutes'", nativeQuery = true)
    Integer updateStatus3To4();

}
