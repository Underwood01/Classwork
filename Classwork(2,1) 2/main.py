import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import numpy as np

np.random.seed(42)
data = {
    'Region': np.random.choice(['North America', 'Europe', 'Asia', 'South America'], size=200),
    'Transaction_Type': np.random.choice(['Deposit', 'Withdrawal', 'Transfer', 'Investment'], size=200),
    'Amount': np.random.randint(100, 5000, size=200)
}
df = pd.DataFrame(data)

pivot_df = df.groupby(['Region', 'Transaction_Type'])['Amount'].sum().unstack(fill_value=0)
pivot_df_perc = pivot_df.div(pivot_df.sum(axis=1), axis=0) * 100

plt.figure(figsize=(12, 7))
pivot_df_perc.plot(kind='bar', stacked=True, colormap='viridis', ax=plt.gca())

plt.title('Percentage Distribution of Transaction Types by Region', fontsize=16)
plt.xlabel('Region', fontsize=14)
plt.ylabel('Percentage of Total Transactions in Region (%)', fontsize=14)
plt.xticks(rotation=45, ha='right')
plt.legend(title='Transaction Type', bbox_to_anchor=(1.05, 1), loc='upper left')

plt.grid(axis='y', linestyle='--', alpha=0.7)
plt.tight_layout()
plt.show() 
