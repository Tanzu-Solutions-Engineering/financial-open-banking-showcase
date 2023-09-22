using System.Runtime.InteropServices;
using System.Reflection;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Solutions.Consumer.Mapping;

namespace Showcase.SteelToe.Data.Solutions.Test
{
    [TestClass]
    public class AccountMapperTest
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
            expected.Data = accountJson;

            Assert.AreEqual(expected,subject.ToAccount(accountJson));
        }
    }
}