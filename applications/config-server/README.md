
Setup 

```text
The following listing shows a recipe for creating the git repository in the preceding example:

$ cd $HOME
$ mkdir config-repo
$ cd config-repo
$ git init .
$ echo info.foo: bar > application.properties
$ git add -A .
$ git commit -m "Add application.properties"
```


Testing

```shell
curl localhost:8888/account-redis-sink/default
```