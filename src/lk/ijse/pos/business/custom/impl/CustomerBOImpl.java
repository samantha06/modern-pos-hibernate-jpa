package lk.ijse.pos.business.custom.impl;

import lk.ijse.pos.business.custom.CustomerBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.DAOTypes;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.db.JPAUtil;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.entity.Customer;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerBOImpl implements CustomerBO {

    private CustomerDAO customerDAO = DAOFactory.getInstance().getDAO(DAOTypes.CUSTOMER);



//    public CustomerBOImpl(){
//        String dao = DAOFactory.getInstance().<String>getDAO(DAOTypes.CUSTOMER);
//    }

    @Override
    public CustomerDTO getCustomerById(String id) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManager();


        entityManager.getTransaction().begin();
            customerDAO.setEntityManager(entityManager);
            Customer customer = customerDAO.find(id);
            CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress());
            entityManager.getTransaction().commit();
            entityManager.close();
            return customerDTO;

    }

    public List<CustomerDTO> getAllCustomers() throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
            customerDAO.setEntityManager(entityManager);
            List<CustomerDTO> customers = customerDAO.findAll().stream().map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress())).collect(Collectors.toList());
            entityManager.getTransaction().commit();
        entityManager.close();
            return customers;

    }

    public void saveCustomer(CustomerDTO dto) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
            customerDAO.setEntityManager(entityManager);
            customerDAO.save(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
            entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void updateCustomer(CustomerDTO dto) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
            customerDAO.setEntityManager(entityManager);
            customerDAO.update(new Customer(dto.getId(), dto.getName(), dto.getAddress()));
            entityManager.getTransaction().commit();
        entityManager.close();

    }

    public void removeCustomer(String id) throws Exception {
        EntityManager entityManager = JPAUtil.getEntityManager();

        entityManager.getTransaction().begin();
            customerDAO.setEntityManager(entityManager);
            customerDAO.delete(id);
            entityManager.getTransaction().commit();
        entityManager.close();

    }

}
