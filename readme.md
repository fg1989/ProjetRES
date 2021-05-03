# Systeme d'envoi de mail

## Description du projet

Dans le cadre du cours de RES, nous mettons en place une application qui permet de faire des blagues en envoyant des mails a des personnes et en leur faisant croire qu’ils proviennent d’un autre expéditeur.

## Mise en place du serveur de Mock

Pour faire des tests de l’applications, il est possible d’utiliser un serveur fictif pour envoyer les mails.
Ce serveur ne transmettra pas les mails à leur destinataire, mais les stockera de manière a ce que l’utilisateur puisse tester l’application.
Pour mettre en place ce système, nous avons choisi d’utiliser [MockMock](https://github.com/tweakers/MockMock).
Pour mettre en place le serveur de test, il faut : build et exécuter le container docker à l'aide des commandes suivantes :

```console
$ docker build . -t mockmock
...
$ docker run --rm -p 8282:8282 -p 10025:25 mockmock
...
```

Dans les paramètres de l’application, il faut indiquer qu’on veut se connecter à l’adresse qui correspond au l’ordinateur sur lequel on fait tourner le container (e.g. `localhost`) et au port numéro 10025.
Une interface web est à disposition au port 8282, elle peut être accedé à l'aide d'un navigateur internet sur le protocole http (e.g. `http://localhost:8282`).

## Mode d'emploi de l'application

Pour utiliser l’application, il suffit de mettre en place les fichiers de configuration et d’exécuter le .jar.
L’application attends à ce que deux fichiers de configuration soit fournis dans le contexte d’exécution : ’adresses.config’ et ’configuration.config’.
Des exemples de ces fichiers sont disponibles à la racine de ce repository.
Le fichier adresses.config contient la liste des adresses des victimes de la blague.
On ne doit mettre qu’une seule adresse par ligne et aucune validation n’est faite sur ces adresses.
Le fichier configuration.config contient le reste de la configuration dans un format XML.
Pour remplir ce fichier, on peut se baser sur l’exemple fournit.
Le paramètre ‘targetMailServer’ sert à indiquer l’adresse du serveur mail que l’on souhaite utiliser.
Le paramètre ’targetMailServerPort’ indique le port sur lequel on souhaite communiquer avec le serveur.
Le paramètre ’realUserName’ indique le nom d’utilisateur avec lequel on souhaite se connecter au serveur.
Le paramètre ’NumberOfGroups’ indique le nombre de groupes de personnes à qui l’on souhaite faire une blague.
Il doit être supérieur à 0.
Le paramètre ’NumberOfTargetsInGroups’ indique le nombre de cibles (personnes qui vont recevoir le message) dans chaque groupe.
Il doit y avoir au moins 2 cibles dans chaque groupe et il faut qu’il y ait au moins une adresse mail de plus que le nombre de cible par groupe qui soit fournie, de manière a ce que chaque groupe soit composé d’un expéditeur et des N cibles.
Enfin le paramètre ’Messages’ contient la liste des différents messages qui peuvent être envoyés.
Chaque message est composé de deux parties ’Subject’, qui contient le titre du message et ’Content’ qui contient le contenu du message.

## Description de l'implémentation

Cette partie du document décrit les classes les plus importantes dans notre implémentation :
Cette partie du document décrit les classes les plus importantes dans notre implémentation :
- La classe SocketReaderWriter permet de lire et d’écrire des lignes depuis un socket.
- La classe SMTPServerConnexion représente une connexion a un serveur SMTP. Elle s’occupe de gérer les détails du protocole SMTP et permet d’envoyer des mails
- La classe SMTPConnexionConfiguration contient les informations demandées par la classe SMTPServerConnexion pour pouvoir se connecter à un serveur SMTP
- La classe SMTPMailInformation contient les informations qui sont nécessaire pour envoyer un mail (destinataire, expéditeur, contenu, titre)
- La classe ConfigurationHelper s’occupe de lire la configuration depuis le fichier de configuration, elle doit être initialisé par la méthode ReadConfiguration(), on peut ensuite utiliser les autres méthodes de la classe pour connaitre les valeurs de la configuration
- La classe App contient la méthode main de l’application et s’occupe de faire le lien entre les différentes parties de l’application.
- Les classes ILogger, StandardLogger et DebugLogger s’occupent de la gestion de l’affichage des information, StandardLogger n’affiche que les erreurs alors que DebugLogger affiche les erreurs ainsi que toutes les communications entre le serveur et l’application
