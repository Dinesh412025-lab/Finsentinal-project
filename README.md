# FinSentinel - AI Finance Controller 🛡️

FinSentinel is an advanced, Java-native multi-agent Artificial Intelligence system designed to act as an autonomous Finance Controller. Built on a Contract Net Protocol (CNP) architecture, it coordinates multiple specialized AI agents to handle real-time financial reconciliation, anomaly detection, cash flow forecasting, and compliance.

## ✨ Features
- **Multi-Agent Architecture**: 5 specialized agents (Reconciliation, Anomaly, Forecasting, Compliance, Orchestrator) communicating via Java Message Broker.
- **Progressive Web App (PWA)**: Installable as a standalone Desktop or Mobile application.
- **Conversational Copilot**: Ask natural language questions about your cash flow or anomalies.
- **Blockchain Audit Trail**: All agent decisions are cryptographically hashed in a tamper-proof ledger.
- **Scenario Simulator**: Real-time What-If sliders for payment delays and volume multipliers.
- **Voice Briefing**: Daily financial summaries read aloud via Web Speech API.
- **Firebase Authentication**: Secure role-based access control (includes a Mock Auth mode for easy local testing).

## 🚀 How to Run Locally

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Dinesh412025-lab/Finsentinal-project.git
   cd Finsentinal-project
   ```

2. **Run the Spring Boot Backend**:
   Ensure you have Java 17+ and Maven installed.
   ```bash
   mvn spring-boot:run
   ```

3. **Access the App**:
   Open your web browser and navigate to:
   👉 **http://localhost:8080**

## 📱 How to Install as an App (PWA)
FinSentinel is built as a Progressive Web App (PWA). This means you can install it directly to your computer or phone so it runs like a native app!
1. Open the app in **Google Chrome** or **Microsoft Edge** (`http://localhost:8080`).
2. Look for the **Install Icon** (a monitor with a downward arrow) in the right side of the address bar.
3. Click **Install**. The app will now appear on your desktop, have its own icon, and run in a standalone window!

## ☁️ Deployment Note
Because FinSentinel requires a live Java server to run its complex multi-agent AI logic, it cannot be hosted on simple static site hosts like GitHub Pages. 
To make this link accessible globally to the public, the repository should be deployed to a cloud hosting platform such as **Render**, **Heroku**, or **Google Cloud Run**.
