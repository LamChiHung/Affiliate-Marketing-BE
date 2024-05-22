package com.sparkminds.affiliatemarketing.service;

import com.sparkminds.affiliatemarketing.entity.Link;
import org.springframework.data.domain.Page;

public interface LinkService {

  String generateRefferalCode();

  Link createNewLink(String productName, String email);

  Link getLinkByProductNameAndRefferalCode(String productName, String refferalCode);

  boolean plusReward(Integer userId);

  Page<Link> getLinkOfPage(Integer page, String username);

  boolean checkLinkIsActive(String productName, String refferalCode);

  void deleteLink(Integer linkId, String email);
}
