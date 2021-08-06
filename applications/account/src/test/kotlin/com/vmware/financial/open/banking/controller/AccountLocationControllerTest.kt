//package com.vmware.financial.open.banking.controller
//
//import com.vmware.financial.open.banking.domain.AccountLocation
//import com.vmware.financial.open.banking.repository.AccountRepository
//import com.vmware.financial.open.banking.repository.LocationRepository
//import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.ArgumentMatchers.any
//import org.mockito.Mock
//import org.mockito.Mockito.never
//import org.mockito.Mockito.verify
//import org.mockito.junit.jupiter.MockitoExtension
//import java.lang.IllegalArgumentException
//
//@ExtendWith(MockitoExtension::class)
//internal class AccountLocationControllerTest{
//    private val invalidZipCode: String = "Invalid";
//    private val validZipCode: String = "09999";
//
//    @Mock
//    private lateinit var locationRepository: LocationRepository;
//    @Mock
//    private lateinit var accountRepository: AccountRepository;
//    private lateinit var subject : AccountController;
//    private lateinit var accountLocation : AccountLocation;
//
//    @BeforeEach
//    internal fun setUp() {
//        subject = AccountController(accountRepository,locationRepository);
//        accountLocation = JavaBeanGeneratorCreator.of(AccountLocation::class.java).create();
//    }
//
//    @Test
//    internal fun saveAccountLocation() {
//        accountLocation.location.zipCode = validZipCode;
//        subject.save(accountLocation);
//        verify(accountRepository).save(any());
//        verify(locationRepository).save(any());
//    }
//
//    @Test
//    internal fun rollback() {
//
//        accountLocation.location.zipCode = invalidZipCode;
//        assertThrows<IllegalArgumentException> { subject.save(accountLocation) }
//        verify(accountRepository).save(any());
//        verify(locationRepository, never()).save(any());
//    }
//}