IRA Rollover Integration - Backend & Workflow Plan
1. Flow of Actions
- Webhook/API receives IRA rollover initiation event from external system (e.g., record keeper).
- Spring Boot controller processes the incoming request and validates the payload.
- Persist initial request details in the database (MongoDB or other).
- Start an Activiti BPMN process instance using the received payload (process variables).
- Activiti workflow drives the lifecycle of the rollover process (statuses: INITIATED → VALIDATION → UW_REVIEW → COMPLETED/FAILED).
- UW (Underwriting) tasks assigned as user tasks inside Activiti.
- Callbacks or APIs exposed for downstream systems (if needed).
- Workflow updates persisted in database and status exposed via status tracker APIs.
2. Clarifications Needed
- Is the integration entry point a Webhook (external system pushes data) or an API (we expose endpoint and external calls explicitly)?
- What system(s) are responsible for providing IRA rollover initiation events (record keeper, custodian, etc.)?
- Is CTH (Case/Transaction Handler) involved in this workflow? If yes, at which stage?
- What exact data model/schema will be received in the webhook/API payload (fields, nested objects)?
- Do we need synchronous responses (ACK/NACK) on webhook receipt, or only async processing?
- What SLAs apply to each step (e.g., validation must complete in X minutes)?
- How should errors and retries be handled? (replay webhook, compensating transaction, manual intervention).
- Do downstream systems (finance, reporting) need to be notified during workflow transitions?
- What environments will this run in (DEV, UAT, PROD), and do we need separate configurations per tenant?
3. Suggested Timeline
- Week 1: Raise access requests, confirm webhook vs API, finalize payload schema with external system.
- Week 2: Set up local development environment (Spring Boot + Activiti + MongoDB).
- Week 3: Implement webhook/API controller, data persistence, and stub BPMN workflow.
- Week 4: Implement detailed BPMN flow (initiation, validation, UW tasks, completion/failure).
- Week 5: Integrate with external teams (record keeper/custodian, finance) for end-to-end flow testing.
- Week 6: Internal QA, UAT with business, fix gaps, prepare deployment pipeline.
- Week 7: Go-live readiness, monitoring setup, rollback plan.
4. BPMN Workflow Diagram
Below is a simplified BPMN-style diagram to illustrate the IRA rollover flow with webhook/API entry, validation, UW review, and outcomes.
 
