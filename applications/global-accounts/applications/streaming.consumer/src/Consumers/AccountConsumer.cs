using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Showcase.SteelToe.Data.Solutions.Domain;
using Showcase.SteelToe.Data.Repository;
using Steeltoe.Stream.Attributes;
using Steeltoe.Stream.Messaging;
using Showcase.SteelToe.Data.Solutions.Consumer.Mapping;

namespace Showcase.SteelToe.Data.Solutions.Consumer.Consumers
{
    [EnableBinding(typeof(ISink))]
    public class AccountConsumer
    {
        private readonly IAccountMapper mapper;

         private readonly ILogger<AccountConsumer> log;

        public delegate IAccountRepository CreateRepository();

        private CreateRepository repositoryCreator;

        public AccountConsumer(IServiceProvider serviceProvider,
            ILogger<AccountConsumer> logger,
            IAccountMapper mapper)
        : this((delegate {
            return serviceProvider.GetRequiredService<IAccountRepository>();
        }),logger,mapper)
        {}

        public AccountConsumer(CreateRepository repositoryCreator,
                    ILogger<AccountConsumer> log,
                    IAccountMapper mapper)
        {
            this.repositoryCreator = repositoryCreator;
            this.log = log;
            this.mapper = mapper;
        }

        [StreamListener(ISink.INPUT)]
        public void Accept(String jsonPayload)
        {
            log.LogInformation($"ACCOUNT: {jsonPayload}");

            var repository = repositoryCreator();

            repository.Save(mapper.ToAccount(jsonPayload));
        } 
    }
}