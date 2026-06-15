package tests.testcases;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("ParaBank Full Regression Test Suite")
@SelectClasses({
        TC01_SuccessfulLogin_Test.class,
        TC02_InvalidLogin_Test.class,
        TC03_Datasource_Test.class,
        TC04_NewDataInput_Test.class,
        TC05_DataListing_Test.class,
        TC06_MultiPageListEntry_Test.class,
        TC07_ModifyExistingData_Test.class,
        TC08_SaveData_Test.class,
        TC09_DeleteDataTest.class,
        TC10_Logout_Test.class,
})
public class AllTestsSuite {
    // This suite class acts as a container to group and run all GUI test cases together
}