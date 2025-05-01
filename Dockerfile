[build 5/7] RUN ./mvnw dependency:go-offline -B 
process "/bin/sh -c ./mvnw dependency:go-offline -B" did not complete successfully: exit code: 126
 

Dockerfile:10

-------------------

8 |

9 |     # Download dependencies first

10 | >>> RUN ./mvnw dependency:go-offline -B

11 |

12 |     # Copy the rest of the source code

-------------------

ERROR: failed to solve: process "/bin/sh -c ./mvnw dependency:go-offline -B" did not complete successfully: exit code: 126