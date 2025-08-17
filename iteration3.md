Here are concise answers for all review questions, referencing the relevant classes and files:

---

### 1. Was the team able to use JDBC to access an SQLite Database?
**Score: 2**  
- **Implemented in:**  
  - `DbConnect.java`: Uses `DriverManager.getConnection(...)` to connect to SQLite via JDBC.  
  - `WorldDaoImpl.java`: Uses JDBC (`Connection`, `PreparedStatement`, `ResultSet`) for database operations.

### 2. Was the team able to implement the Save functionality?  
(World Size, Positions and Sizes of Obstacles (All types))  
**Score: 4**  
- **Implemented in:**  
  - `WorldDaoImpl.saveWorld(...)`: Saves world size and all obstacles using JDBC.  
  - **Tested in:** `WorldDaoImplTest.java`.

### 3. Does the world save successfully by simply using the word "SAVE"?  
**Score: 1**  
- **Implemented in:**  
  - `SaveWorldCommand.java`: Handles "SAVE" command and triggers save logic.  
  - **Tested in:** `SaveWorldCommandTest.java`.

### 4. Was the team able to implement the Restore functionality?  
(World Size, Positions and Sizes of Obstacles (All types))  
**Score: 4**  
- **Implemented in:**  
  - `WorldDaoImpl.loadWorld(...)`: Restores world and obstacles using JDBC.  
  - **Tested in:** `WorldDaoImplTest.java`.

### 5. Does the world get restored successfully by simply using the word "RESTORE"?  
**Score: 1**  
- **Implemented in:**  
  - `RestoreWorldCommand.java`: Handles "RESTORE" command and triggers restore logic.

### 6. Does the Database Schema allow for saving and restoring many different Robot worlds?  
**Score: 2**  
- **Implemented in:**  
  - table.sql: Schema supports multiple worlds.  
  - `WorldDaoImpl.java`: Logic supports saving/restoring multiple worlds.

### 7. Does the Database Schema allow for saving and restoring Robot Worlds by using its unique name saved in the Database?  
**Score: 2**  
- **Implemented in:**  
  - table.sql: World name is a unique key.  
  - `WorldDaoImpl.java`: Uses world name for save/restore operations.

### 8. Is competent Saving messages/error handling implemented when trying to save a world by a name that already exists?  
**Score: 2**  
- **Implemented in:**  
  - `WorldDaoImpl.saveWorld(...)`: Uses `INSERT OR REPLACE` for conflict handling.  
  - `SaveWorldCommand.java`: Prints messages for save status.  
  - **Tested in:** `SaveWorldCommandTest.java`.

---

**Summary:**  
All requirements are implemented and tested.  
**Relevant files:**  
- DbConnect.java  
- WorldDaoImpl.java  
- SaveWorldCommand.java  
- RestoreWorldCommand.java  
- WorldDaoImplTest.java  
- SaveWorldCommandTest.java  
- table.sql