package com.gov.iti.persistence.daos;

import com.gov.iti.business.entities.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public class CartDao extends AbstractDao <Cart>{

    private static final CartDao INSTANCE = new CartDao();
    private CartDao() {
        super(Cart.class);
    }

    public static CartDao getInstance () {
        return INSTANCE;
    }

    public Optional<Cart> getCartByUserId (int userId, EntityManager entityManager) {
        try {
            Cart cart = entityManager.createQuery("SELECT c FROM Cart c WHERE c.user.id = :userId", Cart.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            return Optional.of(cart);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }
}
