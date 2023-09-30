using System.Collections.Generic;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using Showcase.SteelToe.Data.Solutions.Controllers;

namespace Showcase.SteelToe.Data.Solutions.Test.Controllers
{
    [TestClass]
    public class AccountControllerTest
    {

        private Mock<IAccountRepository> repository = new Mock<IAccountRepository>();
        private AccountController subject;
        private Account testData;

        [TestInitialize]
        public void InitializeTestDataController()
        {
            repository = new Mock<IAccountRepository>();
            subject = new AccountController(repository.Object);
            testData = new Account();
            testData.Id = "1";
        }
        
        [TestMethod]
        public void SaveData()
        {
            subject.SaveAccount(testData);

            repository.Verify( repository => repository.Save(It.IsAny<Account>()));
        }

        [TestMethod]
        public void FindData()
        {
            repository.Setup( r => r.FindById("1")).Returns(testData);
            Assert.AreEqual(testData,subject.FindById(testData.Id));
            
        }

          [TestMethod]
        public void FindAll()
        {
            
            List<Account> expected = new List<Account>();
            expected.Add(testData);

            repository.Setup( r => r.FindAll()).Returns(expected);

            Assert.AreEqual(expected,subject.FindAll());
            
        }


        [TestMethod]
        public void FindAll_Empty()
        {
            
            List<Account> expected  = null;
            repository.Setup( r => r.FindAll()).Returns(expected);

            Assert.IsNull(subject.FindAll());
        }


          [TestMethod]
        public void DeleteById()
        {
            subject.DeleteById("1");

            repository.Verify( repository => repository.DeleteById(It.IsAny<string>()));
            
        }
    }
}