using System.Collections.Generic;
using System.Linq;
using Showcase.SteelToe.Data.Solutions.Domain;
using Imani.Solutions.Core.API.Util;

namespace Showcase.SteelToe.Data.Repository
{
    public class AccountDataRepository : IAccountRepository
    {
        private AccountDbContext dbContext;
        private readonly int findAllLimit = new ConfigSettings().GetPropertyInteger("findAllLimit",1000);

        public AccountDataRepository(AccountDbContext dbContext)
        {
            this.dbContext = dbContext;
        }

        public void Save(Account testData)
        {
            var updateData = FindById(testData.Id);

            if(updateData == null)
                dbContext.Add(testData);
            else
                updateData.Data = testData.Data;

            dbContext.SaveChanges();
        }

        public Account FindById(int id)
        {
            return dbContext.Find<Account>(id);
        }

        public void DeleteById(int keyId)
        {
            var deleteRecord = FindById(keyId);
            if(deleteRecord == null)
                return;

            dbContext.Remove(deleteRecord);
            dbContext.SaveChanges();
        }

        public List<Account> FindAll()
        {
           return  dbContext.Account.Select(x => x)
           .OrderBy( x => x.Id)
           .Take(findAllLimit)
           .ToList();
        }
    }
}