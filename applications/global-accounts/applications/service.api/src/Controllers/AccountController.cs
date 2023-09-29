using System.Collections.Generic;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Microsoft.AspNetCore.Mvc;
using System;

namespace Showcase.SteelToe.Data.Solutions.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class AccountController
    {
        private IAccountRepository repository;

        public AccountController(IAccountRepository repository)
        {
            this.repository = repository;
        }

        [HttpPost]
        public void PostData(Account testData)
        {
            this.repository.Save(testData);
        }

        [HttpGet]
          [Route("{id}")] 
        public Account FindById(string id)
        {
            
            return this.repository.FindById(id);
        }

       [HttpGet]
        public List<Account> FindAll()
        {
            var accounts = this.repository.FindAll();

            Console.WriteLine($" returning: {accounts}");

            return accounts;
        }

        [HttpDelete]
         [Route("{id}")] 
        public void DeleteById(string id)
        {
            this.repository.DeleteById(id);
        }
    }
}