package api;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("ParaBank API Tesztcsomag")
@SelectClasses({

        API_TC01_GetCustomerDetails_Test.class,
        API_TC02_GetProfileData_Test.class,
        API_TC03_ModifyProfileData_Test.class,
        API_TC04_CreateAccountChained_Test.class,
        API_TC05_GetAccounts_Test.class
})
public class ApiTestSuite {

}