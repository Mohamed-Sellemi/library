# Java 21 - Formation complète

# Module 3 : Les Records (Suite)

Dans cette partie, nous avons approfondi les Records afin de comprendre leur véritable intérêt dans la conception d'une application Java moderne.

---

# Les Records ne servent pas uniquement à écrire moins de code

Une idée reçue consiste à penser que les Records ont été créés uniquement pour remplacer :

- les getters
- equals()
- hashCode()
- toString()

En réalité, ce n'est pas leur objectif principal.

Leur véritable objectif est d'exprimer une intention.

Lorsque l'on écrit :

```java
public record Author(String firstName, String lastName){}
```

on indique immédiatement au développeur que :

- cet objet est immutable ;
- il représente une valeur ;
- son identité est définie uniquement par ses valeurs ;
- il ne possède pas de cycle de vie.

Le langage documente ainsi directement le modèle métier.

---

# Record Components

Dans une classe classique, on parle d'attributs.

```java
private final String firstName;
```

Dans un Record, on parle de Record Components.

```java
public record Author(
    String firstName,
    String lastName
){}
```

Chaque composant génère automatiquement :

- un attribut privé final ;
- un paramètre du constructeur ;
- un accesseur ;
- une participation au equals();
- une participation au hashCode();
- une participation au toString().

---

# Les accesseurs

Contrairement à une classe classique :

```java
author.getFirstName();
```

un Record utilise :

```java
author.firstName();
```

Il n'existe donc pas de getters au sens classique.

---

# Le constructeur canonique

Le compilateur génère automatiquement un constructeur contenant tous les composants.

Exemple :

```java
public record Author(
    String firstName,
    String lastName
){}
```

Le compilateur génère :

```java
public Author(String firstName, String lastName){
    this.firstName = firstName;
    this.lastName = lastName;
}
```

---

# Le constructeur compact

Le constructeur compact permet :

- de valider les données ;
- de les normaliser ;
- sans avoir à réécrire les affectations.

Exemple :

```java
public record Category(String name){

    public Category{

        name = name.trim();

        if(name.isBlank()){
            throw new IllegalArgumentException();
        }

    }

}
```

Le compilateur ajoute automatiquement :

```java
this.name = name;
```

Il est donc inutile (et interdit) d'affecter directement les attributs.

---

# Validation dans un Record

Le constructeur compact est l'endroit idéal pour :

- vérifier les valeurs ;
- supprimer les espaces inutiles (trim()) ;
- mettre en majuscules ou minuscules ;
- appliquer des règles métier simples.

Le Record garantit ainsi qu'il est impossible de créer un objet invalide.

---

# Les Records peuvent contenir des méthodes

Un Record ne contient pas uniquement des données.

Il peut posséder des comportements liés à ses valeurs.

Exemple :

```java
public record Author(
    String firstName,
    String lastName
){

    public String fullName(){
        return firstName + " " + lastName;
    }

}
```

En revanche, un Record ne doit pas contenir des méthodes modifiant son état.

Exemple interdit par conception :

```java
changeLastName(...)
```

---

# Les Records peuvent implémenter des interfaces

Exemple :

```java
public record Category(String name)
        implements Comparable<Category>{

    @Override
    public int compareTo(Category other){
        return name.compareTo(other.name());
    }

}
```

Un Record peut donc parfaitement participer au polymorphisme.

---

# Les Records ne peuvent pas hériter

Un Record hérite implicitement de :

```java
java.lang.Record
```

Il est donc impossible d'étendre une autre classe.

Cette limitation protège également la philosophie des Value Objects.

---

# Classe ou Record ?

Pour choisir correctement, nous avons construit une méthode de réflexion.

Question 1

L'objet possède-t-il une identité propre ?

Oui

→ Classe

Non

→ Continuer.

---

Question 2

Possède-t-il un cycle de vie ?

Oui

→ Classe

Non

→ Continuer.

---

Question 3

Si une valeur change, crée-t-on un nouvel objet ?

Oui

→ Record

Non

→ Classe.

---

Question 4

Possède-t-il plusieurs états métier ?

Oui

→ Classe

Non

→ Record.

---

# Analyse de notre domaine

Après réflexion, notre modèle devient :

| Objet | Choix | Pourquoi |
|--------|------|-----------|
| Category | Record | Objet valeur immutable |
| Author | Record | Objet valeur défini uniquement par son nom et prénom |
| Address | Record | Une modification crée une nouvelle adresse |
| ISBN | Record | Valeur métier avec validations |
| Title | Record | Valeur métier avec validations |
| Book | Classe | Possède un cycle de vie |
| Loan | Classe | Évolue (retour, retard...) |
| User | Classe | Peut évoluer (actif, suspendu...) |
| Password | Classe | Historique, expiration, mise à jour |

Le choix dépend toujours du métier et non du nom de l'objet.

---

# Primitive Obsession

Nous avons découvert un "Code Smell" très courant.

Une Primitive Obsession consiste à représenter tous les concepts métier avec :

- String
- int
- double
- boolean

Exemple :

```java
String isbn;
```

alors qu'il serait préférable d'utiliser :

```java
Isbn isbn;
```

Cette approche apporte :

- une meilleure lisibilité ;
- des validations centralisées ;
- une meilleure sécurité grâce au compilateur.

---

# Les Value Objects

Un Value Object possède généralement :

- une validation ;
- éventuellement une normalisation ;
- une comparaison basée sur les valeurs ;
- une immutabilité.

Exemples :

- Title
- ISBN
- Email
- PhoneNumber
- Address
- Money

---

# Exemple : Title

Nous avons créé :

```java
public record Title(String title){

    public Title{

        if(title == null || title.isBlank()){
            throw new IllegalArgumentException();
        }

        title = title.trim();

        if(title.length() > 100){
            throw new IllegalArgumentException();
        }

    }

}
```

Le Record garantit qu'un Title invalide ne peut jamais être créé.

Toutes les validations sont regroupées dans le même objet.

---

# Pourquoi remplacer String par Title ?

Avant :

```java
private String title;
```

Book devait constamment vérifier :

- null ;
- vide ;
- longueur.

Après :

```java
private Title title;
```

Book sait qu'il reçoit déjà un objet valide.

Les validations disparaissent progressivement de Book.

Le domaine devient plus propre.

---

# Rich Domain Model

Notre objectif est de déplacer progressivement les règles métier vers les objets concernés.

Au début :

Book validait tout.

```
Book

├── validation du titre
├── validation ISBN
├── validation catégorie
├── validation auteurs
```

Après refactoring :

```
Book

├── Title
│     └── valide le titre
│
├── Isbn
│     └── valide le format
│
├── Author
│     └── garantit un auteur valide
│
├── Category
│     └── garantit une catégorie valide
```

Chaque objet devient responsable de lui-même.

Book ne fait plus que coordonner ces objets.

---

# Password : pourquoi ce n'est plus un Record ?

Au départ, Password pouvait sembler être un Value Object.

Mais au fil de notre conception, nous lui avons ajouté :

- un historique ;
- une date de création ;
- une date d'expiration ;
- une date de modification ;
- une méthode changePassword() ;
- une méthode isExpired().

Password possède désormais un véritable cycle de vie.

Il est donc plus pertinent de le conserver sous forme de classe.

---

# Principes retenus

✔ Un Record représente une valeur.

✔ Une Classe représente généralement une entité.

✔ Un Value Object protège lui-même sa cohérence.

✔ Un objet invalide ne doit jamais pouvoir être créé.

✔ Les validations doivent vivre dans l'objet concerné.

✔ Les objets métier sont préférables aux types primitifs lorsque ceux-ci portent une signification métier.

✔ Le compilateur est un allié : plus le modèle est riche, plus il aide à éviter les erreurs.

✔ Le choix entre Class et Record dépend toujours du métier, jamais uniquement de la syntaxe Java.

---

# Ce qu'il faut retenir (20 % qui apportent 80 % de la valeur)

- Un Record n'est pas seulement une classe plus courte, il exprime une intention.
- Les Records sont parfaits pour représenter les Value Objects.
- Un constructeur compact permet de valider et de normaliser les données sans réécrire les affectations.
- Les validations doivent être centralisées dans les objets métier.
- Remplacer progressivement les String "intelligents" (ISBN, Title, Email...) par des objets métier rend le modèle plus robuste.
- Concevoir un bon modèle objet est souvent plus important que maîtriser la syntaxe avancée de Java.

---

# Erreurs fréquentes en entreprise

❌ Transformer toutes les classes en Record.

❌ Utiliser un Record pour une entité ayant un cycle de vie.

❌ Laisser les validations dispersées dans les Services.

❌ Représenter tous les concepts métier avec des String.

❌ Croire qu'un Record sert uniquement à réduire le nombre de lignes de code.

❌ Choisir entre Class et Record uniquement pour des raisons techniques plutôt que métier.
