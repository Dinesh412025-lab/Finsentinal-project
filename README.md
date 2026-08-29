# FinSentinel - AI Finance Controller 🛡️

**🔴 Live Demo:** [FinSentinel App (Deployed on Render)](https://finsentinal-project.onrender.com/)

FinSentinel is an advanced, Java-native multi-agent Artificial Intelligence system designed to act as an autonomous Finance Controller. Built on a Contract Net Protocol (CNP) architecture, it coordinates multiple specialized AI agents to handle real-time financial reconciliation, anomaly detection, cash flow forecasting, and compliance.

## ✨ Features
- **Multi-Agent Architecture**: 5 specialized agents (Reconciliation, Anomaly, Forecasting, Compliance, Orchestrator) communicating via Java Message Broker.
- **Progressive Web App (PWA)**: Installable as a standalone Desktop or Mobile application.
- **Conversational Copilot**: Ask natural language questions about your cash flow or anomalies.
- **Blockchain Audit Trail**: All agent decisions are cryptographically hashed in a tamper-proof ledger.
- **Scenario Simulator**: Real-time What-If sliders for payment delays and volume multipliers.
- **Voice Briefing**: Daily financial summaries read aloud via Web Speech API.
- **Firebase Authentication**: Secure role-based access control (includes a Mock Auth mode for easy local testing).

## 🚀 How to Deploy to the Internet (Permanently)

FinSentinel comes pre-configured with a `Dockerfile` and a `render.yaml` Blueprint to allow for seamless 1-click deployment to Render.

1. Go to **[Render.com](https://dashboard.render.com)** and sign up using your GitHub account.
2. Click **"New +"** in the top right corner and select **"Blueprint"**.
3. Connect your GitHub account and select this `Finsentinal-project` repository.
4. Render will automatically read the configuration and deploy your Java Spring Boot server!
5. Once finished, Render will provide you with a live, permanent public URL (e.g., `https://finsentinel-xxxx.onrender.com`).

*(You can then share this link with anyone!)*

## 📱 How to Install as an App (PWA)
Because FinSentinel is built as a Progressive Web App (PWA), anyone visiting your live Render link can install it directly to their computer or phone:
1. Open your new live Render URL in **Google Chrome** or **Microsoft Edge**.
2. Look for the **Install Icon** (a monitor with a downward arrow) on the right side of the address bar.
3. Click **Install**. The app will now appear on your desktop, have its own icon, and run in a standalone window!
