
```json

{
  "Id": "Bank|001",
  "Data": "{\"id\": \"01\", \"account_routings\" : [{\"address\" : \"1 st\"},{\"address\" : \"2 st\"}]}"
}
```


## Docker

```shell
dotnet publish --os linux --arch x64 /t:PublishContainer -c Release
```

```shell
docker tag account.global.service:1.0.0 cloudnativedata/account.global.service:1.0.0
docker push cloudnativedata/account.global.service:1.0.0
```
