🏛️ Sistema de Gestão de Usuários — Arquitetura de Microsserviços

📚 Descrição do Projeto

Este projeto implementa uma solução moderna para gestão de informações de usuários utilizando Microsserviços Spring Boot, Angular 17, Oracle Database, Redis Cache e Kubernetes. O sistema permite que atendentes consultem rapidamente dados pessoais, endereços, cartões de crédito e faturas de clientes, com alta performance e escalabilidade

⚙️ Tecnologias Utilizadas

Angular 17 — Front-end SPA

Spring Boot 2.7 — Backend BFF e Microsserviços

Spring WebFlux — Comunicação reativa no BFF

Spring Data JPA — Persistência no Oracle Database

Redis — Cache distribuído para otimizar consultas

Resilience4j — Tolerância a falhas e circuit breaker

Kubernetes — Orquestração de containers e autoescalabilidade

Docker — Ambientes de desenvolvimento local


🧠 Arquitetura Geral

![arquitetura](https://github.com/user-attachments/assets/b0f00b0c-60e8-49a3-8126-8c3b719a7bce)

O fluxo é o seguinte: o usuário realiza uma consulta via Front-end Angular → O BFF (Backend-for-Frontend) verifica o cache Redis → Se não houver dados no cache, ele faz chamadas paralelas aos microsserviços → Cada microsserviço acessa seu próprio banco Oracle → O BFF responde ao Angular com todos os dados consolidados e também salva no Redis para futuras consultas.

Fluxo resumido: Usuário → Angular → BFF (Spring WebFlux) → (Redis Cache → ou → Microsserviços → Oracle DB)

O Kubernetes é responsável por manter todos os serviços vivos, escaláveis, conectados e balanceados.

📦 Microsserviços

Microsserviço	Porta	Responsabilidade	Banco de dados
user-info-service	8081	Nome, CPF, telefone do usuário	Oracle (users)
address-service	8082	Endereço do usuário	Oracle (addresses)
credit-card-service	8083	Cartões de crédito do usuário	Oracle (credit_cards)
invoice-service	8084	Faturas de cartões	Oracle (invoices)
bff-service	8080	Orquestração e cache	Redis (somente cache)

☸️ Kubernetes

Deployments para cada serviço.

Services para comunicação interna (ClusterIP).

Ingress Controller para entrada externa.

Horizontal Pod Autoscaler (HPA) para escalar automaticamente baseando-se em CPU e memória.

ConfigMaps e Secrets para gerenciamento de configurações e credenciais sensíveis.

🚀 Como Executar Localmente

Suba os serviços Redis e Oracle XE com Docker Compose:

docker-compose up -d

Rode cada microsserviço:

./mvnw spring-boot:run

Rode o Front-end Angular:

npm install
ng serve

Acesse a aplicação:

http://localhost:4200

📋 Estrutura de Pastas

bff-service/

user-info-service/

address-service/

credit-card-service/

invoice-service/

frontend-angular/

k8s/                # Manifests Kubernetes

infra/              # Dockerfiles e docker-compose

📢 Diferenciais da Solução

🚀 Alta performance: Redis Cache + WebFlux Reativo

☸️ Escalabilidade automática: Kubernetes HPA

🔥 Resiliência: Resilience4j para circuit breakers e retries

🧩 Arquitetura desacoplada: Cada serviço é independente

🔒 Banco Oracle isolado por serviço: Mais segurança e separação de domínio

📜 Licença

Este projeto é privado e foi desenvolvido para estudos e demonstração de uma arquitetura de microsserviços de alta disponibilidade.

Desenvolvido por Denis Henrique Pacheco da Costa 🚀
