package org.apcffl.api.admin.service.impl;

import java.util.Arrays;
import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;
import org.apcffl.api.config.EmailConfig;
import org.apcffl.api.constants.UIMessages;
import static org.apcffl.api.exception.constants.Enums.ErrorCodeEnums.*;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserGroupRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.manager.EmailManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class AdminServiceImplTest {

	private AdminServiceImpl service;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private OwnerRepository ownerRepository;

	@Mock
	private UserGroupRepository userGroupRepository;
	
	@Mock
	private EmailManager emailManager;
	
	@Mock
	private EmailConfig emailConfig;

	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<UserModel> userCaptor;
	
	@Captor
	private ArgumentCaptor<OwnerModel> ownerCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		service = 
				new AdminServiceImpl(ownerRepository, userRepository, userGroupRepository, emailManager, emailConfig);

	    final Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    logger.setLevel(Level.DEBUG);
	}
	
	@Test
	public void verify_accountRetrieval_notValidGroupTier() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_GUEST);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		assertEquals(null, response.getActiveFlag());
		assertEquals(null, response.getEmail1());
		assertEquals(null, response.getEmail2());
		assertEquals(null, response.getEmail3());
		assertEquals(null, response.getFirstName());
		assertEquals(null, response.getLastName());
		assertEquals(null, response.getLeagueName());
		
		verify(ownerRepository, never()).findByUserName(userNameCaptor.capture());
	}
	
	@Test
	public void testAccountRetrieval_accountNotExist() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = null;
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(AccountError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ACCOUNT_NOT_FOUND, response.getError().getMessage());
		assertEquals(null, response.getActiveFlag());
		assertEquals(null, response.getEmail1());
		assertEquals(null, response.getEmail2());
		assertEquals(null, response.getEmail3());
		assertEquals(null, response.getFirstName());
		assertEquals(null, response.getLastName());
		assertEquals(null, response.getLeagueName());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
	}
	
	@Test
	public void verify_accountRetrieval() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(null, response.getError());
		assertEquals(ApcfflTest.OWNER_ACTIVE, response.getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, response.getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, response.getEmail3());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.getLeagueName());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
	}
	
	@Test
	public void verify_accountRetrievalAll_notValidGroupTier() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_GUEST);
		
		List<OwnerModel> mockOwner = Arrays.asList(ApcfflTest.buildOwnerModel());
		when(ownerRepository.findAll()).thenReturn(mockOwner);
		
		// invoke
		
		AllAccountsResponse response = service.accountRetrievalAll(request);
		
		// verify results
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		assertEquals(null, response.getAccounts());
		
		verify(ownerRepository, never()).findAll();
	}
	
	@Test
	public void verify_accountRetrievalAll() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_ADMIN);
		
		List<OwnerModel> mockOwner = Arrays.asList(ApcfflTest.buildOwnerModel());
		when(ownerRepository.findAll()).thenReturn(mockOwner);
		
		// invoke
		
		AllAccountsResponse response = service.accountRetrievalAll(request);
		
		// verify results
		
		assertEquals(null, response.getError());
		assertEquals(1, response.getAccounts().size());
		assertEquals(ApcfflTest.OWNER_ACTIVE, response.getAccounts().get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getAccounts().get(0).getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, response.getAccounts().get(0).getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, response.getAccounts().get(0).getEmail3());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getAccounts().get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getAccounts().get(0).getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.getAccounts().get(0).getLeagueName());
		
		verify(ownerRepository, times(1)).findAll();
	}
	
	@Test
	public void verify_accountCreate() {
		
		// prepare test data
		
		AccountCreateRequest request = ApcfflTest.buildAccountCreateRequest();
		
		UserGroupModel userGroup = 
				ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_GUEST_ID, 
				ApcfflTest.USER_GROUP_GUEST);
		when(userGroupRepository.findByUserGroupName(anyString()))
		.thenReturn(userGroup);
		
		UserModel user = ApcfflTest.buildUserModel();
		when(userRepository.save(any())).thenReturn(user);
		
		when(ownerRepository.save(any())).thenReturn(new OwnerModel());
		
		doNothing().when(emailManager).sendEmail(anyString(), anyString(), anyString());
		
		// invoke
		
		String result = service.accountCreate(request);
		
		// verify results
		
		assertEquals(UIMessages.MSG_GENERIC_CHECK_EMAIL, result);
		
		verify(userGroupRepository, times(1)).findByUserGroupName(userNameCaptor.capture());
		assertEquals(SecurityConstants.USER_GROUP_GUEST, userNameCaptor.getValue());
		
		verify(userRepository, times(1)).save(userCaptor.capture());
		UserModel resultUser = userCaptor.getValue();
		assertEquals(ApcfflTest.PASSWORD, resultUser.getPassword());
		assertEquals(ApcfflTest.USER_NAME, resultUser.getUserName());
		assertEquals(ApcfflTest.USER_GROUP_GUEST_ID, resultUser.getUserGroupModel().getUserGroupId());
		assertEquals(ApcfflTest.USER_GROUP_GUEST, resultUser.getUserGroupModel().getUserGroupName());
		
		verify(ownerRepository, times(1)).save(ownerCaptor.capture());
		OwnerModel resultOwner = ownerCaptor.getValue();
		assertEquals(false, resultOwner.getActiveFlag());
		assertNotNull(resultOwner.getCreateDate());
		assertEquals(ApcfflTest.OWNER_EMAIL1, resultOwner.getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, resultOwner.getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, resultOwner.getEmail3());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, resultOwner.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, resultOwner.getLastName());
		assertNotNull(resultOwner.getUpdateDate());
		assertEquals(ApcfflTest.PASSWORD, resultOwner.getUserModel().getPassword());
		assertEquals(ApcfflTest.USER_NAME, resultOwner.getUserModel().getUserName());
	}
	
	@Test
	public void verify_accountUpdate_notValidGroupTier() {
		
		//prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_GUEST);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		when(ownerRepository.save(any())).thenReturn(new OwnerModel());
		
		// invoke
		
		AccountResponse response = service.accountUpdate(request);
		
		// verify results
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		verify(ownerRepository, never()).findByUserName(userNameCaptor.capture());
	}
	
	@Test
	public void verify_accountUpdate() {
		
		//prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setUserName(ApcfflTest.USER_NAME);
		request.setEmail1(ApcfflTest.OWNER_EMAIL1 + "request");
		request.setEmail2(ApcfflTest.OWNER_EMAIL2 + "request");
		request.setEmail3(ApcfflTest.OWNER_EMAIL3 + "request");
		request.setFirstName(ApcfflTest.OWNER_FIRST_NAME + "request");
		request.setLastName(ApcfflTest.OWNER_LAST_NAME + "request");
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		when(ownerRepository.save(any())).thenReturn(new OwnerModel());
		
		// invoke
		
		AccountResponse response = service.accountUpdate(request);
		
		// verify results
		
		assertEquals(null, response.getError());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());

		verify(ownerRepository, times(1)).save(ownerCaptor.capture());
		OwnerModel resultOwner = ownerCaptor.getValue();
		assertNotNull(resultOwner.getCreateDate());
		assertEquals(request.getEmail1(), resultOwner.getEmail1());
		assertEquals(request.getEmail2(), resultOwner.getEmail2());
		assertEquals(request.getEmail3(), resultOwner.getEmail3());
		assertEquals(request.getFirstName(), resultOwner.getFirstName());
		assertEquals(request.getLastName(), resultOwner.getLastName());
		assertNotNull(resultOwner.getUpdateDate());
		assertEquals(ApcfflTest.PASSWORD, resultOwner.getUserModel().getPassword());
		assertEquals(ApcfflTest.USER_NAME, resultOwner.getUserModel().getUserName());
		
	}
}
