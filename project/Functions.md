# Functions
Link: `http://127.0.0.1:8080/api`
## Stack holders
- Admin
- Moderator
- Candidate




## Table of Functions
| Function              | METHOD |               API                | Admin | Moderator |  Candidate  | Complete | Handling | Created_at |
|-----------------------|:------:|:--------------------------------:|:-----:|:---------:|:-----------:|:--------:|:--------:|---------|
| getAllCourses         |  GET   |            `/courses`            |   ✅   |   ✅       |    ✅    |    ❌     |    ❌     |         |
| getUserById           |  GET   |       `/user/profile/{id}`       |   ✅   |     ✅     |    ✅    |    ❌     |    ❌     |         |
| updateUserById        |  PUT   |   `/user/update-profile/{id}`    |   ✅   |     ✅     |    ✅    |    ❌     |    ❌     |         |
| getAllusers           |  GET   |             `/users`             |   ✅   |     ❌     |    ❌    |    ❌     |    ❌     |         |
| getALlCandidates      |  GET   |           `/cadidates`           |   ✅   |     ✅     |    ❌    |    ❌     |    ❌     |         |
| getAllModerators      |  GET   |          `/moderators`           |   ✅   |     ❌     |    ❌    |    ❌     |    ❌     |         |
| getCourseById         |  GET   |          `/course/{id}`          |   ✅   |     ✅     |    ✅    |    ❌     |    ❌     |         |
| getCoursesByCandidate |  GET   |    `/candidate/{idc}/courses`    |   ✅   |     ✅     |    ✅    |    ❌     |    ❌     |         |
