import pandas as pd
import numpy as np
import random
from datetime import datetime, timedelta

# Function to generate random datetime within a range
def random_date(start, end):
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = random.randrange(int_delta)
    return start + timedelta(seconds=random_second)

# Generate synthetic data
start_date = datetime(2023, 1, 1)
end_date = datetime(2023, 12, 31)

num_transactions = 1000
fraud_percentage = 0.05  # 5% of transactions are fraudulent

# Generate transaction data
transaction_ids = list(range(1, num_transactions + 1))
dates = [random_date(start_date, end_date) for _ in range(num_transactions)]
amounts = [round(random.uniform(10, 1000), 2) for _ in range(num_transactions)]
merchants = [random.choice(['Amazon', 'Walmart', 'BestBuy', 'Target']) for _ in range(num_transactions)]
categories = [random.choice(['Electronics', 'Clothing', 'Groceries', 'Books']) for _ in range(num_transactions)]

# Assign fraud labels
num_fraudulent = int(num_transactions * fraud_percentage)
fraud_indices = random.sample(range(num_transactions), num_fraudulent)
labels = ['Fraudulent' if i in fraud_indices else 'Legitimate' for i in range(num_transactions)]

# Create DataFrame
data = pd.DataFrame({
    'TransactionID': transaction_ids,
    'Date': dates,
    'Amount': amounts,
    'Merchant': merchants,
    'Category': categories,
    'Label': labels
})

# Save to CSV
data.to_csv('fraud_detection_data.csv', index=False)