package io.kx.loanapp.action;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.Empty;
import io.kx.loanapp.api.LoanAppApi;
import kalix.javasdk.action.ActionCreationContext;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your io/kx/loanapp/action/loan_app_action.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class LoanAppServiceActionImpl extends AbstractLoanAppServiceAction {

  private static Logger logger = LoggerFactory.getLogger(LoanAppServiceActionImpl.class);

  public LoanAppServiceActionImpl(ActionCreationContext creationContext) {}

  @Override
  public Effect<Empty> submitLoanApp(LoanAppApi.SubmitCommand submitCommand) {

    CompletionStage timeRegistration = timers().startSingleTimer(submitCommand.getLoanAppId(), 
                              Duration.ofSeconds(20),
                              components().loanAppServiceActionImpl().expireAction(
                                LoanAppApi.DeclineCommand.newBuilder()
                                  .setLoanAppId(submitCommand.getLoanAppId())
                                  .setReason("Declined loan app after not approved within 20 secs")
                                  .build())
                                );
    return effects().asyncReply(
      timeRegistration.thenCompose(done -> components().loanAppEntity().submit(submitCommand).execute())
      .thenApply(any -> Empty.getDefaultInstance())
    );

  }

  @Override
  public Effect<Empty> expireAction(LoanAppApi.DeclineCommand declineCommand) {
    logger.info("############################");
    logger.info("############################");
    logger.info("Expiring loan app " + declineCommand.getLoanAppId());
    logger.info("############################");
    logger.info("############################");
    return effects().forward(components().loanAppEntity().decline(declineCommand));
  }


}
