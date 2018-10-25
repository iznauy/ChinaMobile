package top.iznauy.chinamobile.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import top.iznauy.chinamobile.entity.PositivePhoneCall;

/**
 * Created on 2018/10/25.
 * Description:
 *
 * @author iznauy
 */
public interface PositivePhoneCallJPA extends JpaRepository<PositivePhoneCall, PositivePhoneCall.PositivePhoneCallKey> {
}
