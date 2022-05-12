import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.shared.protocols.Request;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProtocolTest {
 @Test
 public void testSerializationForward() {
  Request request = new Request("sisipho","forward");
  assertEquals("{\"robotName\":\"sisipho\",\"command\":\"forward\"}",request.serialize());

 }
 @Test
 public void testSerializationFire() {
  Request request = new Request("maggie","fire");
  assertEquals("{\"robotName\":\"maggie\",\"command\":\"fire\"}",request.serialize());

 }
 @Test
 public void testSerializationLook() {
  Request request = new Request("morgan","look");
  assertEquals("{\"robotName\":\"morgan\",\"command\":\"look\"}",request.serialize());

 }
 @Test
 public void testSerializationRepair() {
  Request request = new Request("mfundo","repair");
  assertEquals("{\"robotName\":\"mfundo\",\"command\":\"repair\"}",request.serialize());

 }
 @Test
 public void testSerializationLeft() {
  Request request = new Request("morgan","left");
  assertEquals("{\"robotName\":\"morgan\",\"command\":\"left\"}",request.serialize());

 }
 @Test
 public void testSerializationDump() {
  Request request = new Request("mfundo","dump");
  assertEquals("{\"robotName\":\"mfundo\",\"command\":\"dump\"}",request.serialize());

 }
 @Test
 public void testSerializationReload() {
  Request request = new Request("maggie","reload");
  assertEquals("{\"robotName\":\"maggie\",\"command\":\"reload\"}",request.serialize());

 }
 @Test
 public void testSerializationRight() {
  Request request = new Request("sisipho","right");
  assertEquals("{\"robotName\":\"sisipho\",\"command\":\"right\"}",request.serialize());

 }
 @Test
 public void testSerializationRobot() {
  Request request = new Request("mfundo","robot");
  assertEquals("{\"robotName\":\"mfundo\",\"command\":\"robot\"}",request.serialize());

 }
 @Test
 public void testSerializationState() {
  Request request = new Request("morgan","state");
  assertEquals("{\"robotName\":\"morgan\",\"command\":\"state\"}",request.serialize());

 }


}
