Choix techniques :
Architecture MVVM : mis à part le fait qu'il s'agisse d'une contrainte, l'avantage de cette architecture me permet de récupérer des données distantes, de distribuer ces données à un intermédiaire
puis de les faire passer aux différents view model correspondant aux différentes vues. Cela permet de dissocier totalement la vue (qui a besoin des données) de la méthode (qui permet de les récupérer).
En plus de cela, si la vue (l'ui) venait à être obsolète, il n'y aura qu'a "plug in" nos données inchangées à la nouvelle ui.

Binding : viewBinding true, j'ai activé le viewBinding car cela réduisait la quantité de ligne nécessaire à l'initialisation des éléments. A la place d'avoir pour chaque element une variable dont le
type est spécifié suivi d'un fidViewById (et si j'avais eu besoin que ces variables soient globale j'aurais du mettre un autre bloc de private lateInit var), je n'ai eu qu'a initialiser mon binding,
(3 lignes).

Singleton pattern : étant donné que nous récupérons les données en background, que cela peut prendre du temps et que l'utilisateur peut changer d'activité avant la récupération des résultats,
il est (de mon avis) judicieux de s'assurer que l'on ne créer pas une multitude d'instances de la class qui s'occupe de récupérer les données ainsi que les repository. Le but étant d'éviter
le gaspillage de ressources.

Injection de dépendances : DestinationApi(private val fakeDestinationFetchingService: FakeDestinationFetchingService), la class DestinationApi, qui permet de recuperer les données distantes,
reçoit plutôt que créer l'objet FakeDestinationFetchingService, de cette manière on obtient plus de modularité et la class est plus facile à tester. Première utilisation.

Difficultés :
Les tests unitaires des view models utilisant coroutine ne sont pas encore maitrisés. Coroutine étant un pan d'apprentissage que j'ai appris pour la contrainte du test, la manière de créer les 
tests n'ai pas encore au point (cela ne saurait tardé mais tout de même). Cependant, l'utilisation de coroutine en lui même, de sa logique, de ses dispatcher et autres s'est réalisée sans difficultés.

Dans l'ensemble le test m'a paru assez simple (les résultats me diront si j'ai eu raison de le penser).

Plus de temps :
Si le temps alloué était plus important, j'aurais d'abord approfondi le sujet de coroutine, savoir si je devrais lancer CoroutineScope.launch à partir de DestinationApi et retourner un LiveData
ou bien comme je l'ai implémenté c'est correct (ou au moins, pas négatif).
J'aurais appris avec mon mentor les tests à effectuer pour un view model ou coroutine est présent 
J'aurai rajouté des animations.
