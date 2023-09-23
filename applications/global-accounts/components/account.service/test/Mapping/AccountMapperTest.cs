using System.Runtime.InteropServices;
using System.Reflection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Solutions.Consumer.Mapping;

namespace Showcase.SteelToe.Data.Solutions.Test.Mapping
{
    [TestClass]
    public class AccountMapperTest
    {
         private string expectedJson = @"
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

        private string missingBankIdJson = @"
        {
            ""id"": ""001"",
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

             private string missingIdJson = @"
        {
           
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

        private AccountMapper subject;


        [TestInitialize]
        public void InitializeAccountMapperTest()
        {
            subject = new AccountMapper();
        }

        [TestMethod]
        public void ToAccount()
        {
            var expected = new Account();
            expected.Id = "FLT|001";
            expected.Data = expectedJson;

            var actual = subject.ToAccount(expectedJson);

            Assert.AreEqual(expected.Id,actual.Id);
            Assert.AreEqual(expectedJson,actual.Data);
        }

        [TestMethod]
        public void GivenMissingId_ThenThrowException()
        {
            Assert.ThrowsException<ArgumentException>(
              () => {
                subject.ToAccount(missingIdJson);
              }  
            );            
            
        }

        [TestMethod]
        public void GivenMissingBankId_ThenThrowException()
        {
            Assert.ThrowsException<ArgumentException>(
              () => {
                subject.ToAccount(missingBankIdJson);
              }  
            );            
            
        }
    }
}