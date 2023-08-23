package io.kx.loanapp.domain;

import com.google.protobuf.Empty;
import io.kx.loanapp.api.LoanAppApi;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import kalix.javasdk.testkit.EventSourcedResult;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.UUID;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanAppEntityTest {

  @Test
  @Ignore("to be implemented")
  public void exampleTest() {
    LoanAppEntityTestKit service = LoanAppEntityTestKit.of(LoanAppEntity::new);
    // // use the testkit to execute a command
    // // of events emitted, or a final updated state:
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // EventSourcedResult<SomeResponse> result = service.someOperation(command);
    // // verify the emitted events
    // ExpectedEvent actualEvent = result.getNextEventOfType(ExpectedEvent.class);
    // assertEquals(expectedEvent, actualEvent);
    // // verify the final state after applying the events
    // assertEquals(expectedState, service.getState());
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  public void submitTest() {
    LoanAppEntityTestKit service = LoanAppEntityTestKit.of(LoanAppEntity::new);
    String loanAppId = UUID.randomUUID().toString();
    LoanAppApi.SubmitCommand submitCommand = LoanAppApi.SubmitCommand.newBuilder()
      .setLoanAppId(loanAppId)
      .setClientId("clientId")
      .setClientMonthlyIncomeCents(200000)
      .setLoanAmountCents(5000000)
      .setLoanDurationMonths(20)
      .build();

    EventSourcedResult<Empty> result = service.submit(submitCommand);
    assertTrue(result.didEmitEvents());

    LoanAppApi.GetCommand getCommand = LoanAppApi.GetCommand.newBuilder()
      .setLoanAppId(loanAppId).build();

    EventSourcedResult<LoanAppApi.LoanAppState> getResult = service.get(getCommand);
    assertEquals(LoanAppApi.LoanAppStatus.STATUS_IN_REVIEW, getResult.getReply().getStatus());

  }


  @Test
  @Ignore("to be implemented")
  public void getTest() {
    LoanAppEntityTestKit service = LoanAppEntityTestKit.of(LoanAppEntity::new);
    // GetCommand command = GetCommand.newBuilder()...build();
    // EventSourcedResult<LoanAppState> result = service.get(command);
  }


  @Test
  @Ignore("to be implemented")
  public void approveTest() {
    LoanAppEntityTestKit service = LoanAppEntityTestKit.of(LoanAppEntity::new);
    // ApproveCommand command = ApproveCommand.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.approve(command);
  }


  @Test
  @Ignore("to be implemented")
  public void declineTest() {
    LoanAppEntityTestKit service = LoanAppEntityTestKit.of(LoanAppEntity::new);
    // DeclineCommand command = DeclineCommand.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.decline(command);
  }

}
