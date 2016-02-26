package tiscon1.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tiscon1.form.AccountForm;
import tiscon1.form.AccountRegisterForm;
import tiscon1.form.LoginForm;
import tiscon1.model.Customer;
import tiscon1.model.UserPrincipal;
import tiscon1.model.modeltop10.CategoryRepositorytop10;
import tiscon1.repository.CustomerRepository;

import javax.servlet.http.HttpSession;

/**
 * @author kawasima
 */
@Controller
public class AccountController {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CategoryRepositorytop10 categoryRepositorytop10;
    @ModelAttribute
    AccountRegisterForm setupForm() {
        return new AccountRegisterForm();
    }

    @RequestMapping(value="/login")
    public String login(@Validated LoginForm form, BindingResult bindingResult, HttpSession session) {
        Customer customer = customerRepository.findOne(Specifications
                .where((Specification<Customer>) (root, query, cb) ->
                        cb.equal(root.get("name"), form.getAccount()))
                .and((root, query, cb) ->
                        cb.equal(root.get("password"), form.getPassword())
                ));
        if (customer != null) {
            session.setAttribute("principal", new UserPrincipal(customer.getName()));
            return "redirect:/my/account?id=" + customer.getId();
        } else {
            return "newAccountOrSignIn";
        }
    }

    @RequestMapping(value="/loginforwishlist")
    public String loginforwishlist(@Validated LoginForm form, BindingResult bindingResult, HttpSession session) {
        Customer customer = customerRepository.findOne(Specifications
                .where((Specification<Customer>) (root, query, cb) ->
                        cb.equal(root.get("name"), form.getAccount()))
                .and((root, query, cb) ->
                        cb.equal(root.get("password"), form.getPassword())
                ));
        if (customer != null) {
            session.setAttribute("principal", new UserPrincipal(customer.getName()));
            return "redirect:/my/wishlist?id=" + customer.getId();
        } else {
            return "newAccountOrSignIn";
        }
    }

    /**
     * Logout.
     *
     * @param session
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("principal");
        return "redirect:/";
    }

    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String newAccountOrSignIn() {
        return "newAccountOrSignIn";
    }

    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String register(@Validated AccountRegisterForm form, BindingResult bindingResult
    , HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "newAccountOrSignIn";
        }
        Customer customer = new Customer(form.getName(), form.getEmail(), form.getPassword());
        customerRepository.save(customer);
        UserPrincipal principal = new UserPrincipal(form.getName());
        session.setAttribute("principal", principal);
        return "redirect:/my/account?id=" + customer.getId();
    }

    @RequestMapping(value="/my/account", method=RequestMethod.GET)
    public String editAccount(@RequestParam("id") Long customerId,
                         @ModelAttribute("principal") UserPrincipal principal,
                         Model model) {
        if (customerRepository.exists(customerId)) {
            Customer customer = customerRepository.findOne(customerId);
            model.addAttribute("customer", customer);
            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(customer, accountForm);
            model.addAttribute("accountForm", accountForm);
            return "customerAccount";
        } else {
            return "error";
        }
    }

    @RequestMapping(value="/my/account", method=RequestMethod.POST)
    public String saveAccount(@Validated AccountForm form, BindingResult bindingResult) {
        return "customerAccount";
    }

    @RequestMapping("/my/orders")
    public String showOrders(@RequestParam("id") Long customerId,
                             @ModelAttribute("principal") UserPrincipal principal,
                             Model model) {
        if (customerRepository.exists(customerId)) {
            Customer customer = customerRepository.findOne(customerId);
            model.addAttribute("customer", customer);
            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(customer, accountForm);
            model.addAttribute("accountForm", accountForm);
            return "customerOrders";
        } else {
            return "error";
        }
    }

    @RequestMapping("/my/wishlist")
    public String showWishlist(@RequestParam("id") Long customerId,
                               @ModelAttribute("principal") UserPrincipal principal,
                               Model model) {
        if (customerRepository.exists(customerId)) {
            Customer customer = customerRepository.findOne(customerId);
            model.addAttribute("customer", customer);
            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(customer, accountForm);
            model.addAttribute("accountForm", accountForm);
            return "customerWishlist";
        } else {
            return "error";
        }
    }

    @RequestMapping("/my/basket")
    public String showbasket(@RequestParam("id") Long customerId,
                               @ModelAttribute("principal") UserPrincipal principal,
                               Model model) {
        if (customerRepository.exists(customerId)) {
            Customer customer = customerRepository.findOne(customerId);
            model.addAttribute("customer", customer);
            AccountForm accountForm = new AccountForm();
            BeanUtils.copyProperties(customer, accountForm);
            model.addAttribute("accountForm", accountForm);
            return "basket.html";
        } else {
            return "error";
        }
    }

}
