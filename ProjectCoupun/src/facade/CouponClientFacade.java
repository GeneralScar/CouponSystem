package facade;

import infra.CLIENT_TYPE;

public interface CouponClientFacade {
public CouponClientFacade login (String name, String password, CLIENT_TYPE clientType);
}
