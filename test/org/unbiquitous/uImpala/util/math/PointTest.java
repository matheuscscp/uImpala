package org.unbiquitous.uImpala.util.math;

import static org.fest.assertions.api.Assertions.*;
import org.junit.Test;

public class PointTest {

	@Test public void stores2DInfo(){
		assertThat(new Point(20,30).x).isEqualTo(20);
		assertThat(new Point(15,26).y).isEqualTo(26);
	}
	
	
	@Test public void addsTwoPoints(){
		assertThat(new Point(1,1).add(new Point(1,1))).isEqualTo(new Point(2,2));
		assertThat(new Point(1,2).add(new Point(3,4))).isEqualTo(new Point(4,6));
		Point original = new Point(1,2);
		original.add(new Point(3,5));
		assertThat(original).isEqualTo(new Point(4,7));
	}
	
	@Test public void calculatesTheDistanceBetweenTwoPoints(){
		assertThat(new Point(1,1).distanceTo(new Point(1,1))).isEqualTo(0);
		assertThat(new Point(10,0).distanceTo(new Point(20,0))).isEqualTo(10);
		assertThat(new Point(0,40).distanceTo(new Point(0,70))).isEqualTo(30);
		assertThat(new Point(15,5).distanceTo(new Point(35,0))).isEqualTo(25);
		assertThat(new Point(35,0).distanceTo(new Point(15,5))).isEqualTo(25);
	}
	
	@Test public void calculatesThemModule(){
		assertThat(new Point(10,0).module()).isEqualTo(10);
		assertThat(new Point(0,15).module()).isEqualTo(15);
		assertThat(new Point(10,15).module()).isEqualTo(25);
		assertThat(new Point(-10,-20).module()).isEqualTo(30);
	}
	
	@Test public void pointIsClonable(){
		Point original = new Point(1,2);
		Point clone = original.clone();
		assertThat(original).isEqualTo(clone);
		clone.x = 3;
		clone.y = 5;
		assertThat(original).isNotEqualTo(clone);
	}
	
	@Test public void hasAnAppropriatedStringRepresentation(){
		assertThat(new Point(15,5).toString()).isEqualTo("(15,5)");
	}
}
