package com.sparkminds.affiliatemarketing.service.imp;

import com.sparkminds.affiliatemarketing.constance.Constance;
import com.sparkminds.affiliatemarketing.entity.Link;
import com.sparkminds.affiliatemarketing.entity.User;
import com.sparkminds.affiliatemarketing.repository.LinkRepository;
import com.sparkminds.affiliatemarketing.repository.UserRepository;
import com.sparkminds.affiliatemarketing.service.LinkService;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkServiceImp implements LinkService {

  @Autowired
  LinkRepository linkRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  EntityManager entityManager;

  public String generateRefferalCode() {
    int leftLimit = 48; // numeral '0'
    int rightLimit = 122; // letter 'z'
    int targetStringLength = 10;
    Random random = new Random();
    return random.ints(leftLimit, rightLimit + 1)
        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
        .limit(targetStringLength)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  public Link createNewLink(String productName, String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NullPointerException("User not exist"));
    Optional<Link> existLink = linkRepository.findByProductNameAndAndUserId(productName,
        user.getId());
    if (existLink.isPresent()) {
      if (existLink.get().isDeleted()) {
        existLink.get().setDeleted(false);
        existLink.get().setCreatedDate(LocalDate.now());
        linkRepository.save(existLink.get());
        return existLink.get();
      } else {
        return null;
      }
    } else {
      Link link = new Link();
      link.setUserId(user.getId());
      link.setDeleted(false);
      link.setProductName(productName);
      link.setCreatedDate(LocalDate.now());
      link.setLink(generateLink(productName, user.getRefferalCode()));
      link = linkRepository.save(link);
      return link;
    }
  }

  public Link getLinkByProductNameAndRefferalCode(String productName, String refferalCode) {
    productName = productName.replaceAll("-", " ");
    User user = userRepository.findByRefferalCode(refferalCode)
        .orElseThrow(() -> new NullPointerException("User not exist"));
    return linkRepository.findByProductNameAndAndUserId(productName, user.getId())
        .orElseThrow(() -> new NullPointerException("Link not exist"));
  }

  public String generateLink(String productName, String refferalCode) {
    productName = productName.replaceAll(" ", "-");
    return (Constance.domain + "/links/" + productName + "/" + refferalCode);
  }

  public boolean checkLinkIsActive(String productName, String refferalCode) {
    Link link = getLinkByProductNameAndRefferalCode(productName, refferalCode);
    return !link.isDeleted();
  }

  public Page<Link> getLinkOfPage(Integer page, String username) {
    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new NullPointerException("User not found"));
    Pageable pageable = PageRequest.of(page, Constance.linkPageSize);
    return linkRepository.findAllByIsDeletedAndUserId(false, user.getId(), pageable);
  }

  @Transactional(isolation = Isolation.SERIALIZABLE)
  public boolean plusReward(Integer userId) {
    User user = userRepository.findWithLockingById(userId)
        .orElseThrow(() -> new NullPointerException("USer not found"));
    user.setReward(user.getReward() + 1);
    userRepository.save(user);
    return true;
  }

  public void deleteLink(Integer linkId, String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NullPointerException("User not found"));
    Link link = linkRepository.findByIdAndUserId(linkId, user.getId())
        .orElseThrow(() -> new NullPointerException("Link not found"));
    link.setDeleted(true);
    linkRepository.save(link);
  }
}
