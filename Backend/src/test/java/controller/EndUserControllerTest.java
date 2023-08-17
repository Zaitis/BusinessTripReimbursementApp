package controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class EndUserControllerTest {
    /**
     * Method under test: {@link EndUserController#handleDisplayReimbursements()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testHandleDisplayReimbursements() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.RuntimeException: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "REIMBURSEMENTS" not found (this database is empty); SQL statement:
        //   SELECT * FROM reimbursements [42104-220]
        //       at repository.ReimbursementRepository.getReimbursements(ReimbursementRepository.java:156)
        //       at service.ReimbursementService.getAllReimbursements(ReimbursementService.java:25)
        //       at controller.EndUserController.handleDisplayReimbursements(EndUserController.java:81)
        //   org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "REIMBURSEMENTS" not found (this database is empty); SQL statement:
        //   SELECT * FROM reimbursements [42104-220]
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:514)
        //       at org.h2.message.DbException.getJdbcSQLException(DbException.java:489)
        //       at org.h2.message.DbException.get(DbException.java:223)
        //       at org.h2.message.DbException.get(DbException.java:199)
        //       at org.h2.command.Parser.getTableOrViewNotFoundDbException(Parser.java:8548)
        //       at org.h2.command.Parser.getTableOrViewNotFoundDbException(Parser.java:8532)
        //       at org.h2.command.Parser.readTableOrView(Parser.java:8521)
        //       at org.h2.command.Parser.readTablePrimary(Parser.java:1911)
        //       at org.h2.command.Parser.readTableReference(Parser.java:2391)
        //       at org.h2.command.Parser.parseSelectFromPart(Parser.java:2844)
        //       at org.h2.command.Parser.parseSelect(Parser.java:2950)
        //       at org.h2.command.Parser.parseQueryPrimary(Parser.java:2834)
        //       at org.h2.command.Parser.parseQueryTerm(Parser.java:2690)
        //       at org.h2.command.Parser.parseQueryExpressionBody(Parser.java:2669)
        //       at org.h2.command.Parser.parseQueryExpressionBodyAndEndOfQuery(Parser.java:2662)
        //       at org.h2.command.Parser.parseQueryExpression(Parser.java:2655)
        //       at org.h2.command.Parser.parseQuery(Parser.java:2624)
        //       at org.h2.command.Parser.parsePrepared(Parser.java:732)
        //       at org.h2.command.Parser.parse(Parser.java:697)
        //       at org.h2.command.Parser.parse(Parser.java:669)
        //       at org.h2.command.Parser.prepareCommand(Parser.java:577)
        //       at org.h2.engine.SessionLocal.prepareLocal(SessionLocal.java:634)
        //       at org.h2.engine.SessionLocal.prepareCommand(SessionLocal.java:557)
        //       at org.h2.jdbc.JdbcConnection.prepareCommand(JdbcConnection.java:1116)
        //       at org.h2.jdbc.JdbcPreparedStatement.<init>(JdbcPreparedStatement.java:92)
        //       at org.h2.jdbc.JdbcConnection.prepareStatement(JdbcConnection.java:288)
        //       at repository.ReimbursementRepository.getReimbursements(ReimbursementRepository.java:133)
        //       at service.ReimbursementService.getAllReimbursements(ReimbursementService.java:25)
        //       at controller.EndUserController.handleDisplayReimbursements(EndUserController.java:81)
        //   See https://diff.blue/R013 to resolve this issue.

        (new EndUserController()).handleDisplayReimbursements();
    }
}

