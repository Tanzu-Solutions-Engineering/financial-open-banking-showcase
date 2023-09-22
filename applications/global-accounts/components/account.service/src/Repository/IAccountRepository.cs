using System.Collections.Generic;
using Showcase.SteelToe.Data.Solutions.Domain;

namespace Showcase.SteelToe.Data.Repository
{
    public interface IAccountRepository
    {
        void Save(Account value);

        List<Account> FindAll();

        Account FindById(int id);
        

         void DeleteById(int id);
    }
}