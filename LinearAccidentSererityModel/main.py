import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from joblib import dump, load 


df = pd.read_csv('accident_data.csv')


df.dropna(inplace=True)


dependent_variable = 'Casualties_Count' 
independent_variables = ['Speed_Limit', 'Vehicles_Involved', 'Is_Daylight_Numerical'] # Example X

X = df[independent_variables]
Y = df[dependent_variable]


X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.2, random_state=42)


model = LinearRegression()


model.fit(X_train, Y_train)


print(f"Model Intercept: {model.intercept_}")s
print(f"Model Coefficients: {dict(zip(independent_variables, model.coef_))}")


model_filename = 'linear_regression_accident_severity_model.joblib'
dump(model, model_filename)

print(f"Model successfully saved as {model_filename}")


loaded_model = load('linear_regression_accident_severity_model.joblib')


hypothetical_data = pd.DataFrame([[80, 5, 1]], 
                                  columns=independent_variables)


predicted_severity = loaded_model.predict(hypothetical_data)

print("\n--- Prediction Example ---")
print(f"Hypothetical Inputs: {hypothetical_data.to_dict(orient='records')[0]}")
print(f"*Predicted Accident Severity (Casualties): {predicted_severity[0]:.2f}*")