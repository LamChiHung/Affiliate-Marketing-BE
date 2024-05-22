package com.sparkminds.affiliatemarketing.controller;

import com.sparkminds.affiliatemarketing.constance.Constance;
import com.sparkminds.affiliatemarketing.dto.ResponseDto;
import com.sparkminds.affiliatemarketing.dto.request.CreateLinkDto;
import com.sparkminds.affiliatemarketing.dto.response.ConfirmPurchaseDto;
import com.sparkminds.affiliatemarketing.entity.Link;
import com.sparkminds.affiliatemarketing.service.AuthService;
import com.sparkminds.affiliatemarketing.service.LinkService;
import com.sparkminds.affiliatemarketing.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/links")
public class LinkController {

  @Autowired
  LinkService linkService;
  @Autowired
  AuthService authService;
  @Autowired
  UserService userService;

  @PostMapping(produces = "application/json")
  public ResponseEntity<?> createLink(@Valid @RequestBody CreateLinkDto createLinkDto,
      Authentication authentication) {
    Link link = linkService.createNewLink(createLinkDto.getProductName(),
        authentication.getName());
    if (link != null) {
      return ResponseEntity.ok()
          .body(ResponseDto.builder().statusCode(Constance.successStatusCode).data(link).build());
    } else {
      return ResponseEntity.ok()
          .body(ResponseDto.builder().statusCode(Constance.errorStatusCode).build());
    }
  }

  @GetMapping()
  public ResponseEntity<?> getLinks(@RequestParam(name = "page", defaultValue = "0") Integer page,
      Authentication authentication) {
    String username = authentication.getName();
    Page<Link> links = linkService.getLinkOfPage(page, username);
    ResponseDto<?> response = ResponseDto.builder()
        .statusCode(Constance.successStatusCode)
        .data(links).build();
    return ResponseEntity.ok().body(response);
  }

  @DeleteMapping("/{linkId}")
  public ResponseEntity<?> deleteLink(@PathVariable("linkId") Integer linkId,
      Authentication authentication) {
    linkService.deleteLink(linkId, authentication.getName());
    ResponseDto<?> response = ResponseDto.builder().statusCode("00").message("Link is deleted")
        .build();
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{productName}/{refferalCode}")
  public ResponseEntity<?> getLink(@PathVariable("productName") String productName,
      @PathVariable("refferalCode") String refferalCode) {
    Link link = linkService.getLinkByProductNameAndRefferalCode(productName, refferalCode);
    String name = userService.getNameByRefferalCode(refferalCode);
    if (link.isDeleted()) {
      ResponseDto<?> response = ResponseDto.builder().statusCode(Constance.errorStatusCode)
          .message("The link is no longer available!").build();
      return ResponseEntity.ok().body(response);
    }
    ConfirmPurchaseDto confirmPurchaseDto = ConfirmPurchaseDto.builder()
        .productName(link.getProductName()).sellerName(name).build();
    ResponseDto<?> response = ResponseDto.builder().statusCode(Constance.successStatusCode)
        .data(confirmPurchaseDto).build();
    return ResponseEntity.ok().body(response);
  }

  @PostMapping("/{productName}/{refferalCode}")
  public ResponseEntity<?> buyProduct(@PathVariable("productName") String productName,
      @PathVariable("refferalCode") String refferalCode) {
    Integer userId = authService.getUserIdByRefferalCode(refferalCode);
    boolean isValid = linkService.checkLinkIsActive(productName, refferalCode);
    if (isValid) {
      boolean isSuccess = linkService.plusReward(userId);
      ResponseDto<?> responseDto;
      if (isSuccess) {
        responseDto = ResponseDto.builder().statusCode(Constance.successStatusCode)
            .message("Purchase successful!").build();
      } else {
        responseDto = ResponseDto.builder().statusCode(Constance.errorStatusCode)
            .message("The link is not valid!").build();
      }
      return ResponseEntity.ok().body(responseDto);
    } else {
      ResponseDto<?> responseDto = ResponseDto.builder().statusCode(Constance.errorStatusCode)
          .message("The link is no longer available!").build();
      return ResponseEntity.ok().body(responseDto);
    }
  }
}
