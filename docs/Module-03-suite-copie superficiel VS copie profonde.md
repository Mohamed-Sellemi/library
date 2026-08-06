# Java 21 - Formation complète
## Résumé - Module 3 (Suite) : Records, immutabilité et copies

---

# Objectifs de cette partie

Dans cette partie, nous avons approfondi les notions de Record afin de comprendre qu'un Record n'est pas seulement un raccourci d'écriture, mais un véritable outil de modélisation.

Nous avons également découvert plusieurs notions fondamentales qui seront utilisées tout au long de la formation :

- l'immutabilité
- les copies défensives
- les références Java
- les copies superficielles (Shallow Copy)
- les copies profondes (Deep Copy)

---

# 1. Un Record n'est pas forcément profondément immutable

Nous avons vu qu'un Record rend **ses attributs immuables**, mais cela ne signifie pas que tous les objets qu'il contient sont immuables.

Exemple :

```java
public record Authors(List<Author> authors) {
}
```

Le champ `authors` est `final`.

En revanche, la `List` reste modifiable.

Exemple :

```java
List<Author> list = new ArrayList<>();

Authors authors = new Authors(list);

list.add(new Author("Albert", "Camus"));
```

Le contenu du Record change alors que le Record lui-même n'a jamais été modifié.

Le problème vient du partage de la même référence vers la liste.

---

# 2. Shallow Immutability

On parle d'immutabilité superficielle lorsque :

- les références ne peuvent plus changer
- mais les objets référencés peuvent évoluer

Exemple :

```java
record Team(List<Player> players) {}
```

Le Record est immutable.

La List ne l'est pas.

Le résultat est donc une **Shallow Immutability**.

---

# 3. Deep Immutability

Pour qu'un objet soit profondément immutable, il faut :

- que ses références soient immuables
- que les objets qu'il contient soient immuables
- que les collections soient protégées

Exemple :

```java
public record Authors(List<Author> authors) {

    public Authors {
        authors = List.copyOf(authors);
    }

}
```

Le constructeur réalise une copie défensive.

Le Record ne partage plus la même liste que l'appelant.

---

# 4. Copie défensive

Lorsqu'une classe reçoit une collection, il est dangereux de conserver directement la référence.

Mauvais exemple :

```java
this.authors = authors;
```

Bon exemple :

```java
this.authors = new ArrayList<>(authors);
```

Ainsi :

- la classe possède sa propre collection
- personne ne peut modifier sa collection interne par accident.

---

# 5. Différence entre new ArrayList<>(...) et List.copyOf(...)

Ces deux méthodes créent une nouvelle collection.

En revanche, leur comportement est différent.

## new ArrayList<>(...)

- crée une nouvelle liste
- la liste reste modifiable
- réalise une copie superficielle

Exemple :

```java
List<String> copy = new ArrayList<>(list);
```

On peut toujours faire :

```java
copy.add("Java");
```

---

## List.copyOf(...)

- crée une nouvelle liste
- la liste est non modifiable
- réalise également une copie superficielle

Exemple :

```java
List<String> copy = List.copyOf(list);
```

Toute tentative de modification provoque :

```text
UnsupportedOperationException
```

---

# 6. Les deux méthodes réalisent une Shallow Copy

Ni `new ArrayList<>(...)` ni `List.copyOf(...)` ne recopient les objets contenus.

Exemple :

```java
Author author = new Author("Victor", "Hugo");

List<Author> list1 = new ArrayList<>();
list1.add(author);

List<Author> list2 = new ArrayList<>(list1);
```

Les deux listes sont différentes.

En revanche elles contiennent la même référence vers `Author`.

Schéma :

```
List1 -----> Author

List2 -----> Author
```

Les collections sont indépendantes.

Les objets contenus sont partagés.

---

# 7. Shallow Copy

Une copie superficielle recopie uniquement la collection.

Les objets contenus ne sont pas copiés.

Schéma :

```
Nouvelle List

↓

Même Author
```

---

# 8. Deep Copy

Une copie profonde recopie :

- la collection
- tous les objets qu'elle contient

Schéma :

```
Nouvelle List

↓

Nouvel Author

↓

Nouvelle Address
```

Java ne réalise jamais de Deep Copy automatiquement.

Cette logique appartient au développeur.

---

# 9. Pourquoi String, Integer et LocalDate n'ont pas de copyOf()

Parce que ces objets sont déjà immuables.

Partager leur référence n'est jamais dangereux.

Exemple :

```java
String a = "Java";

String b = a;
```

Les deux variables partagent le même objet.

Aucun risque puisqu'il est impossible de modifier une String.

Même principe pour :

- Integer
- Long
- Double
- LocalDate
- LocalDateTime

---

# 10. == vs equals()

Nous avons rappelé la différence fondamentale.

## ==

Compare les références.

Exemple :

```java
list1 == list2
```

Retour :

```text
false
```

Les deux listes sont différentes.

---

## equals()

Compare le contenu.

Exemple :

```java
list1.equals(list2)
```

Java compare :

- la taille
- puis chaque élément avec son equals()

Le résultat est :

```text
true
```

si les contenus sont identiques.

---

# 11. Ce qu'il faut retenir

Lorsque l'on manipule un objet Java, il faut toujours se poser trois questions.

## Première question

L'objet est-il :

- mutable ?

ou

- immutable ?

---

## Deuxième question

Est-ce que je partage une référence ?

ou

Est-ce que je crée une copie ?

---

## Troisième question

Si je crée une copie :

est-elle

- Shallow Copy ?

ou

- Deep Copy ?

Ces trois questions reviennent constamment dans :

- les Streams
- Spring Boot
- les API REST
- les Design Patterns
- le multithreading
- les Virtual Threads

---

# 12. Les Records sont des Value Objects

Un Record représente une valeur.

Son identité est définie par ses attributs.

Exemples de notre projet :

- Title
- Isbn
- Author
- Category
- Address (plus tard)

Ces objets :

- ne possèdent pas de cycle de vie
- ne changent pratiquement jamais
- lorsqu'une valeur change, on crée un nouvel objet.

---

# 13. Les Entités

Une entité possède :

- une identité propre
- un cycle de vie
- plusieurs états possibles

Exemples de notre projet :

- Book
- User
- Loan
- Password

Ces objets évoluent dans le temps.

Ils sont donc modélisés avec des classes classiques.

---

# Conclusion

À la fin de cette partie, je suis capable de :

- expliquer la différence entre une Entité et un Value Object ;
- expliquer pourquoi un Record n'est pas forcément profondément immutable ;
- distinguer Shallow Immutability et Deep Immutability ;
- réaliser une copie défensive ;
- expliquer la différence entre `new ArrayList<>(...)` et `List.copyOf(...)` ;
- expliquer la différence entre Shallow Copy et Deep Copy ;
- expliquer pourquoi `String`, `LocalDate` et `Integer` n'ont pas de méthode `copyOf()` ;
- expliquer la différence entre `==` et `equals()`;
- choisir quand utiliser un Record et quand utiliser une classe.
