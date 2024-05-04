# Online Banking

## Customer record, balance, deposit, withdraw, and internal and external transfer

*Function list*:
- sign up accounts for new customers and cancel accounts for existing customers
- Every customer has one chequing account and one saving account.
- For every account, customers can look up the **balance**, **deposite**, **withdraw** and **transfer** money.
- Customers can **transfer money to other customers**.
- Option to **save** current customers and their account balance.
- Option to **reload** previously saved customers and their account balance.

Like existing online banking application, banks can provide their customers with this application for the purpose of convenient banking.
Moreover, the bank itself can use this application to track customer records. I am interested in this project because I used to wonder how my bank's mobile banking applications work. 


#Phase 4: Task 2:
Wed Nov 24 21:35:25 PST 2021
Customer rock deposited 100.0 into chequing

Wed Nov 24 21:35:26 PST 2021
Customer rock deposited 100.0 into saving

Wed Nov 24 21:35:26 PST 2021
Customer [name = rock, chequing balance = $100.00, saving balance = $100.00] was added into file

Wed Nov 24 21:35:30 PST 2021
Customer jack deposited 100.0 into chequing

Wed Nov 24 21:35:31 PST 2021
Customer jack deposited 100.0 into saving

Wed Nov 24 21:35:31 PST 2021
Customer [name = jack, chequing balance = $100.00, saving balance = $100.00] was added into file

Wed Nov 24 21:35:38 PST 2021
Customer rock transferred 10.0 from chequing to jack's chequing

#Phase 4: Task 3:

*Refactoring list*:
- A "BankingOperation" class can be refactored out from the GUI class. All the banking operations in GUI should be in a separate class.
- Those repeated codes of "Transfer" methods in Account class, which realize the function of Transfer and EMT, can be avoided by few helper methods.
- All repeated instantiations of new Event object and calls to Eventlog.getInstance in Account class can be avoided by one or two helper methods.
