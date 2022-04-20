# Currency-exchange

REST documentation:


## Get the currency rate and gold
 ### POST
`POST` `/exchange`

#### Request

```json
{
      
       "baseCurrency": "PLN",
       "targetCurrency": "USD",
       "exchangeDate": "2022-04-13"
   }

```
##### Response

```json
{
  "baseCurrency": "PLN",
  "targetCurrency": "USD",
  "rate": 0.233507,
  "exchangeDate": "2022-04-13",
}
```
### GET - currency when we choose date

`GET` `/exchange/history/{base}/{target}/{date}`

### Parameters

##### base - base currency
##### target- target currency
##### date- checking course data


#### Example

`GET` `/exchange/history/PLN/USD/2022-04-17`

#### Response

```json
{
  "baseCurrency": "PLN",
  "targetCurrency": "USD",
  "rate": 0.233507,
  "exchangeDate": "2022-04-17"

}

```
### GET - gold when we choose date

`GET` `/exchange/history/gold/{date}`

### Parameters

##### date- checking course data


#### Example

`GET` `/exchange/history/gold/2022-04-17`

#### Response

```json
{
  "baseCurrency": "PLN",
  "targetCurrency": "USD",
  "rate": 0.233507,
  "exchangeDate": "2022-04-17"

}

```

### GET - currency for the present day

`GET` `/exchange/latest/{base}/{target}`

### Parameters

##### base - base currency
##### target- target currency



#### Example

`GET` `/exchange/latest/PLN/USD`

#### Response

```json
{
  "baseCurrency": "PLN",
  "targetCurrency": "USD",
  "rate": 0.233507,
  "exchangeDate": "2022-04-17"

}

```


### GET - gold for the present day

`GET` `/exchange/history/gold/{date}`



#### Example

`GET` `/exchange/latest/gold`

#### Response

```json
{
  "baseCurrency": "XAU",
  "targetCurrency": "PLN",
  "rate": 270.56,
  "exchangeDate": "2022-04-19"

}

```
## Get statistic about currency rate and gold

### GET 
`GET` `/statistic/{base}/{target}/{date}`

#### Example

`GET` `/statistic/PLN/USD/2022-04-16`

#### Response

```json
{
  "baseCurrency": "PLN",
  "targetCurrency": "USD",
  "exchangeDate": "2022-04-16",
  "count": 2
}

```




## Get data from DB

### GET - all currency rate and gold from DB
`GET` `/exchange/all/currency`


#### Response

```json
[
  {
    "id": 135,
    "baseCurrency": "PLN",
    "targetCurrency": "USD",
    "exchangeDate": "2022-04-19"
  },
  {
    "id": 144,
    "baseCurrency": "PLN",
    "targetCurrency": "USD",
    "exchangeDate": "2022-04-13"
  },
  {
    "id": 150,
    "baseCurrency": "PLN",
    "targetCurrency": "XAU",
    "exchangeDate": "2022-04-19"
  }
]


```


### GET - currency queries

`GET` `/statistic/all/queries`


#### Response

```json
[
  {
    "id": 135,
    "baseCurrency": "PLN",
    "targetCurrency": "USD",
    "exchangeDate": "2022-04-19"
  },
  {
    "id": 136,
    "baseCurrency": "PLN",
    "targetCurrency": "USD",
    "exchangeDate": "2022-04-19"
  }
]

```

## Clean DB

### Clean DB with currency 

`DELETE` `/exchange/remove`

### Remove all statistic record from DB

`DELETE` `/statistic/remove`
