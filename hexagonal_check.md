## Hexagonal repositories analysed 22
## Alegro Internal - 18

1. Not a hexagonal architecture - 10 

| Port feature                | Numbers of repos | Allegro Internal |
|:----------------------------|:----------------:|:----------------:|
| Accepts aggregate           |        9         |        0         |
| Not addressed, fields usage |        2         |        0         |
| Accepts dto/command         |        11        |        14        |
| Inside domain               |        11        |        12        |
| Application layer           |        11        |        2         |


| Adapter feature                             | Numbers of repos | Allegro Internal |
|:--------------------------------------------|:----------------:|:----------------:|
| Application level, use case adapter         |        3         |        2         |
| Implements Port interface?                  |        6         |        2         |
| Infrastructure level, protocol, persistence |        13        |        11        |
| Doesn't implement Port interface            |        16        |        11        |
| Separate UseCase concept                    |        15        |        7         |




1. There are cases when Adapter implements persistence Port but not UseCase and UseCase[Service/Handler]s are used - num.repos - 9, allegro - 5
2. Incoming Ports treated differently to outcoming. Repository ports are in the domain, but use cases ports are in the application layer. 
Or implement only one side ports (outcoming) - num.repos - 11, allegro - 13  
3. Passing aggregate to the Port interface leads to invoking aggregate in the infrastructure layer
