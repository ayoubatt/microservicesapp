# 📚 Microservices Library Application

## 👤 Auteur
- Nom & Prénom : Ayoub Attar
- Profil : Data & Software Engineer
- Date : Janvier 2026

## 📌 Description du Projet
Application de gestion de bibliothèque basée sur une architecture microservices, développée avec Spring Boot, Spring Cloud, Eureka, Kafka et MySQL.

Fonctionnalités principales :
- Gérer les utilisateurs
- Gérer les livres
- Gérer les emprunts
- Envoyer des notifications asynchrones lors de la création d’un emprunt

Bonnes pratiques appliquées :
- Database per Service
- Communication asynchrone avec Kafka
- Découverte de services via Eureka
- API Gateway comme point d’entrée unique
- Conteneurisation avec Docker Compose

## 🎯 Objectifs du Projet
- Mettre en place une architecture microservices complète
- Séparer les responsabilités métier par service
- Assurer la persistance des données avec MySQL (une base par service)
- Implémenter une communication asynchrone via Kafka
- Déployer l’ensemble avec Docker & Docker Compose
- Tester l’intégration complète (Gateway → Services → DB → Kafka)

---

## 🏗️ Architecture Générale

### 🔹 Microservices
| Service              | Port  | Rôle                                      |
|----------------------|-------|-------------------------------------------|
| Eureka Server        | 8761  | Découverte et enregistrement des services |
| Gateway Service      | 9999  | Point d’entrée unique et routage          |
| User Service         | 8082  | Gestion des utilisateurs                  |
| Book Service         | 8081  | Gestion des livres                        |
| Emprunt Service      | 8085  | Gestion des emprunts                      |
| Notification Service | 8086  | Consommateur Kafka (notifications)        |

### 🗄️ Base de Données (Database per Service)
Chaque microservice possède sa propre base MySQL :

| Service         | Base de données | Port |
|-----------------|------------------|------|
| User Service    | db_user          | 3307 |
| Book Service    | db_book          | 3308 |
| Emprunt Service | db_emprunter     | 3309 |

📸 Vérification réelle des données (exemple) :
- Utilisateur : Alice Martin
- Livre : Microservices Patterns
- Emprunt : enregistré avec user_id = 1 et book_id = 1

### 🧩 Infrastructure
- Zookeeper (2181) et Kafka (9092)
- 3 instances MySQL (une par service métier)

### 🗂️ Structure du Projet
```
microservicesapp/
├── eurika/               # Eureka Server
├── gateway/              # Spring Cloud Gateway
├── user/                 # User Service
├── book/                 # Book Service
├── emprunter/            # Emprunt Service
├── notification/         # Notification Service
├── docker-compose.yaml   # Orchestration Docker
└── README.md
```

---

## 🔄 Communication Asynchrone avec Kafka
- Topic Kafka : `emprunt-created`
- Producteur : `emprunt-service`
- Consommateur : `notification-service`

### 📦 Format du message Kafka
```json
{
  "empruntId": 1,
  "userId": 1,
  "bookId": 1,
  "eventType": "EMPRUNT_CREATED",
  "timestamp": "2025-01-01T14:00:00"
}
```

Lorsque un emprunt est créé :
1. L’emprunt est sauvegardé en base
2. Un événement est publié sur Kafka
3. Le `notification-service` consomme l’événement et affiche une notification dans les logs

---

## 🧰 Technologies Utilisées
- Java 17+
- Spring Boot 3.4.1
- Spring Cloud (Eureka, Gateway)
- Spring Data JPA
- MySQL 8
- Apache Kafka
- Docker & Docker Compose
- Maven 3.6+

## ⚙️ Prérequis
- Docker 20.x+
- Docker Compose 3.8+
- Java 17+
- Maven 3.6+

---

## 🚀 Installation et Exécution
1) Cloner le projet
```bash
git clone <URL_DU_REPO>
cd microservicesapp
```

2) Démarrer tous les services
```bash
docker-compose up --build
```

⏳ Le démarrage complet peut prendre 2 à 3 minutes.

---

## ✅ Vérification du Déploiement
### Eureka Dashboard
👉 http://localhost:8761

Services attendus :
- EUREKA-SERVICE
- GATEWAY-SERVICE
- USER-SERVICE
- BOOK-SERVICE
- EMPRUNT-SERVICE
- NOTIFICATION-SERVICE

### Gateway Health Check
```bash
curl http://localhost:9999/actuator/health
```
Réponse attendue :
```json
{ "status": "UP" }
```

---

## 🧪 Tests des Endpoints
### 👤 User Service
Créer un utilisateur :
```bash
curl -X POST http://localhost:9999/user-service/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice Martin",
    "email": "alice@example.com"
  }'
```

Récupérer tous les utilisateurs :
```bash
curl http://localhost:9999/user-service/api/users
```

### 📘 Book Service
Créer un livre :
```bash
curl -X POST http://localhost:9999/book-service/api/books/add \
  -H "Content-Type: application/json" \
  -d '{
    "titre": "Microservices Patterns"
  }'
```

Récupérer tous les livres :
```bash
curl http://localhost:9999/book-service/api/books/all
```

### 🔁 Emprunt Service
Créer un emprunt (déclenche Kafka) :
```bash
curl -X POST http://localhost:9999/emprunt-service/emprunts/1/1
```

Récupérer tous les emprunts :
```bash
curl http://localhost:9999/emprunt-service/emprunts
```

---

## 🔔 Vérification Kafka & Notification Service
Logs du Notification Service
```bash
docker logs notification-service
```

Vérifier les topics Kafka / consommer :
```bash
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092

docker exec -it kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic emprunt-created \
  --from-beginning
```

---

## 🗄️ Accès aux Bases MySQL
### User DB
```bash
docker exec -it mysql-user mysql -uroot -proot db_user
SELECT * FROM u;
```

### Book DB
```bash
docker exec -it mysql-book mysql -uroot -proot db_book
SELECT * FROM book;
```

### Emprunt DB
```bash
docker exec -it mysql-emprunt mysql -uroot -proot db_emprunter
SELECT * FROM emprunter;
```

---

## 🛑 Arrêter l’Application
```bash
docker-compose down
```
Supprimer aussi les volumes :
```bash
docker-compose down -v
```

---

## 🧠 Points Clés à Retenir
- Architecture microservices complète
- Database per Service
- Kafka pour l’asynchronisme
- Eureka + Gateway
- Déploiement 100 % Dockerisé
- Projet fonctionnel et testé

## 🔗 Lien du Projet
👉 (à compléter avec ton GitHub / GitLab)
