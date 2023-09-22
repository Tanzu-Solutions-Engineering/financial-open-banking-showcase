using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Microsoft.EntityFrameworkCore;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Showcase.SteelToe.Data.test.Repository
{
    [TestClass]
    public class AccountRepositoryTest
    {
        private AccountDbContext dbContext;
        private AccountDataRepository subject;
        // private Moq.EntityFrameworkCore.<SampleContext> dbContext;
        private DbContextOptions options;
        private Account testData;

        [TestInitialize]
        public void InitializeTestDataEfcRepositoryTest()
        {
             options =  new DbContextOptionsBuilder<AccountDbContext>()
            .UseInMemoryDatabase(databaseName: "SampleDatabase")
            .Options;

            dbContext = new AccountDbContext(options);

            subject = new AccountDataRepository(dbContext);

            testData = new Account();
        }

        [TestMethod]
        public void TestSaveData()
        {
            subject.Save(testData);
            var actual = subject.FindById(testData.Id);

            Assert.AreEqual(testData.Id, actual.Id);
        }

         [TestMethod]
        public void UpdateData()
        {
            var testDataUpdate = new Account();
            testData.Id = 2;
            testData.Data = "test update";

            subject.Save(testData);

            subject.Save(testData);
        }

        [TestMethod]
        public void TestDelete()
        {

            var testDataUpdate = new Account();
            testData.Id = 3;
            testData.Data = "test update";

            subject.Save(testData);

             var actual = subject.FindById(testData.Id);

            Assert.AreEqual(testData.Id, actual.Id);

            subject.DeleteById(testData.Id);

            Assert.IsNull(subject.FindById(testData.Id));
        }

         [TestMethod]
        public void TestFindAll()
        {

            var testDataUpdate = new Account();
            testData.Id = 4;
            testData.Data = "test update";

            subject.Save(testData);

             var actual = subject.FindAll();

            Assert.IsNotNull(actual);
        }
    }
}