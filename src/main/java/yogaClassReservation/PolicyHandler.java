package yogaClassReservation;

import yogaClassReservation.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    private AlarmRepository alarmRepository;


    private void addNotificationHistory(String receiver, String message) {
        Alarm alarm = new Alarm();
        alarm.setReciever(String.valueOf(receiver));
        alarm.setMessage(message);
        alarmRepository.save(alarm);
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentSucceed_RequestAlarm(@Payload PaymentSucceed paymentSucceed){

        if(paymentSucceed.isMe()){
            System.out.println("##### listener RequestAlarm : " + paymentSucceed.toJson());

            addNotificationHistory("(guest)" + paymentSucceed.getGuestAddress(), paymentSucceed.getReserveId() + " : PaymentSucceed");
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCanceled_RequestAlarm(@Payload PaymentCanceled paymentCanceled){

        if(paymentCanceled.isMe()){
            System.out.println("##### listener RequestAlarm : " + paymentCanceled.toJson());

            addNotificationHistory("(guest)" + paymentCanceled.getGuestAddress(), paymentCanceled.getReserveId() + " : PaymentCanceled");
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserved_RequestAlarm(@Payload Reserved reserved){

        if(reserved.isMe()){
            System.out.println("##### listener RequestAlarm : " + reserved.toJson());

            addNotificationHistory("(guest)" + reserved.getGuestAddress(), reserved.getReserveId() + " : Reserved");
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReserveCanceled_RequestAlarm(@Payload ReserveCanceled reserveCanceled){

        if(reserveCanceled.isMe()){
            System.out.println("##### listener RequestAlarm : " + reserveCanceled.toJson());

            addNotificationHistory("(guest)" + reserveCanceled.getGuestAddress(), reserveCanceled.getReserveId() + " : ReserveCanceled");
        }
    }

}
