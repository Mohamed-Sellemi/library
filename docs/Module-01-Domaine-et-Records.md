# Java 21 - Formation complète

# Module 1 : Concevoir le domaine

Avant d'écrire du code, il faut comprendre le métier.

Le développeur ne crée pas des classes.
Il modélise un domaine.

---

# Notre projet

Nous développons une bibliothèque de gestion des livres.

Le domaine est constitué de :

- Book
- Author
- Category
- User
- Loan
- Password

---

# Entité vs Objet Valeur (Value Object)

C'est probablement la notion la plus importante du module.

## Une Entité

Une entité possède une identité propre.

Même si ses attributs changent, elle reste la même.

Exemples :

- Book
- User
- Loan
- Password

Une entité possède généralement :

- un cycle de vie
- plusieurs états
- des comportements métier

Exemple :

Book

```
Livre créé

↓

Le titre change

↓

La catégorie change

↓

Des auteurs sont ajoutés

↓

Le livre reste le même livre.
```

---

## Un Objet Valeur (Value Object)

Un objet valeur est défini uniquement par ses valeurs.

Si une valeur change :

on crée un nouvel objet.

Exemples :

- Author
- Category
- Address
- ISBN

Deux objets ayant les mêmes valeurs représentent la même chose.

Exemple :

```
Author(
    "Victor",
    "Hugo"
)

==

Author(
    "Victor",
    "Hugo"
)
```

---

# Les responsabilités d'une classe

Une classe doit protéger son propre état.

Les validations doivent être réalisées dans la classe.

Exemple :

```
Book.rename(...)
```

vérifie que le nouveau titre est valide.

La classe garantit toujours sa cohérence.

---

# Encapsulation

Mauvaise idée :

```
book.setTitle(...)
```

Meilleure idée :

```
book.rename(...)
```

Pourquoi ?

Parce que :

rename représente une action métier.

set représente uniquement une affectation.

Le nom d'une méthode doit exprimer une intention.

---

# Les setters

Nous avons appris qu'un setter n'est pas toujours une bonne pratique.

Préférer des méthodes métier :

✔ rename()

✔ addAuthor()

✔ removeAuthor()

✔ changeCategory()

plutôt que :

✘ setTitle()

✘ setCategory()

---

# Validation

Nous avons appris à éviter les grosses méthodes de validation.

Mauvais exemple :

```
validate(...)
```

Meilleure approche :

```
validateTitle()

validateIsbn()

validateCategory()

validateAuthor()
```

Puis :

```
validateBook(...)
```

appelle toutes les petites méthodes.

Cette approche permet :

- de réutiliser les validations
- de simplifier le code
- d'améliorer les tests

---

# Collections

Toujours protéger les collections.

Mauvais :

```
return authors;
```

Bon :

```
return List.copyOf(authors);
```

Et dans le constructeur :

```
this.authors = new ArrayList<>(authors);
```

Pourquoi ?

Pour éviter qu'un code externe modifie l'état interne du Book.

---

# equals() et hashCode()

Nous avons choisi :

Book

→ égalité basée sur ISBN

Pourquoi ?

Parce que deux livres ayant le même ISBN représentent le même livre.

Author

→ égalité basée sur :

- prénom
- nom

Category

→ égalité basée sur :

- nom

Loan

→ égalité basée sur UUID

---

# Optional

Nous avons découvert une bonne pratique importante.

Ne jamais écrire :

```
Optional<Champ>
```

comme attribut.

Exemple :

```
private Optional<LocalDate> dateRetour;
```

est une mauvaise pratique.

Préférer :

```
private LocalDate dateRetour;
```

Puis :

```
public Optional<LocalDate> getDateRetour() {
    return Optional.ofNullable(dateRetour);
}
```

Optional est destiné principalement aux valeurs de retour des méthodes.

---

# Password

Nous avons extrait Password de User.

Pourquoi ?

Parce qu'il possède :

- des validations
- un historique
- une expiration
- des comportements

Le User délègue la gestion du mot de passe.

C'est le principe de responsabilité unique (SRP).

---

# Les Records (Java 21)

Un Record représente une donnée.

Il est idéal pour représenter un Value Object.

Exemples :

✔ Author

✔ Category

✔ Address

✔ ISBN

---

# Pourquoi Oracle a créé les Records ?

Ce n'est pas seulement pour écrire moins de code.

Les Records permettent d'exprimer une intention.

Quand on voit :

```
record Address(...)
```

on comprend immédiatement que :

- c'est immutable
- il n'y a pas de setter
- il n'y a pas de cycle de vie
- l'égalité est basée sur les valeurs

Le langage documente le modèle.

---

# Classe ou Record ?

Poser les questions suivantes :

1. Possède-t-il une identité propre ?

→ Oui : Classe

→ Non : Continuer

---

2. Possède-t-il un cycle de vie ?

→ Oui : Classe

→ Non : Continuer

---

3. Si une valeur change, crée-t-on un nouvel objet ?

→ Oui : Record

→ Non : Classe

---

4. Possède-t-il plusieurs états ?

→ Oui : Classe

→ Non : Record

---

# Records

Un Record génère automatiquement :

- constructeur
- equals()
- hashCode()
- toString()
- accesseurs

Exemple :

```
public record Author(
    String firstName,
    String lastName
){}
```

Le compilateur génère :

- firstName()
- lastName()

et non :

getFirstName()

---

# Constructeur compact

Exemple :

```
public record Category(String name){

    public Category{

        if(name == null || name.isBlank()){
            throw new IllegalArgumentException();
        }

        name = name.trim();

    }

}
```

Le compilateur réalise automatiquement :

```
this.name = name;
```

Il ne faut jamais écrire :

```
this.name = ...
```

dans un constructeur compact.

---

# Primitive Obsession

Une mauvaise pratique consiste à tout représenter avec :

- String
- int
- double

Préférer des objets métier.

Exemple :

Mauvais :

```
String isbn;
```

Meilleur :

```
record Isbn(String value){}
```

Autres exemples :

- Email
- PhoneNumber
- Money
- Address
- Title

Ces objets centralisent :

- les validations
- les comportements
- les règles métier

---

# Rich Domain Model

Nous avons progressivement remplacé :

```
String password
```

par

```
Password
```

Puis demain :

```
String isbn
```

par

```
ISBN
```

L'objectif est d'obtenir un modèle métier riche.

Le domaine porte les règles métier.

Ce n'est pas le Service qui doit tout faire.

---

# Les principes retenus

✔ Le domaine protège toujours son état.

✔ Une méthode doit représenter une action métier.

✔ Une classe ne doit jamais être incohérente.

✔ Une validation appartient à l'objet concerné.

✔ Les Records représentent des valeurs.

✔ Les Classes représentent des entités.

✔ Un bon modèle objet vaut souvent plus que des centaines de lignes de Streams.