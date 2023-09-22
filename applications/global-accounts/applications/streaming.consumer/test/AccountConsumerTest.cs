using Microsoft.Extensions.Logging;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Moq;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Showcase.SteelToe.Data.Solutions.Consumer.Consumers;
using Showcase.SteelToe.Data.Solutions.Consumer.Mapping;

namespace streaming.consumer.test
{
    [TestClass]
    public class AccountConsumerTest
    {
        private string accountJson = @"
        {
            ""id"": ""001"",
            ""bank_id"" :  ""FLT"",
            ""user_id"": ""imani"",
            ""label"": ""imani-001"",
            ""product_code"": ""CHECKING"",
            ""balance"": {
                ""amount"": 25000,
                ""currency"": ""US""
            },
            ""account_routings"": [
                {
                ""address"": ""1 Straight Street Newark, NJ"",
                ""scheme"": ""direct""
                }
            ],
            ""branch_id"": ""BRANCH-BROOK""
            }";
        
        
        private Mock<IAccountRepository> repository;
        private Mock<ILogger<AccountConsumer>>  log;

        private Mock<IAccountMapper> mapper;

        private AccountConsumer subject;

        [TestInitialize]
        public void InitializeAccountConsumerTest()
        {
            log = new Mock<ILogger<AccountConsumer>>();
            repository = new Mock<IAccountRepository>();
            mapper = new Mock<IAccountMapper>();

            subject = new AccountConsumer(
                            delegate (){
                                return repository.Object;
                            },
                            log.Object,
                            mapper.Object);
        }

        [TestMethod]
        public void Accept()
        {
            // mapper.Setup( m => m )
            subject.Accept(accountJson);

            mapper.Verify( m => m.ToAccount(It.IsAny<string>()));

            repository.Verify( repository => repository.Save(It.IsAny<Account>()));
        }
    }
}