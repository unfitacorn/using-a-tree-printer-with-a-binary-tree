package cs21120.assignment.solution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import cs21120.assignment.CompetitionManager;
import cs21120.assignment.IBinaryTree;
import cs21120.assignment.IManager;
import cs21120.assignment.Match;
import cs21120.assignment.NoNextMatchException;

public class BTSingleElimrdm10 implements IManager {
	Node root;
	Node current;
	Stack<Node> st = new Stack();
	ArrayList<posit> position = new ArrayList<posit>();
	int pos = 0;
	Queue<Node> queue = new LinkedList<Node>();
	Boolean queueAndStackSet = false;
	Boolean firstTime = true;

	public static void main(String[] args) throws IOException {

		BTSingleElimrdm10 btSingleElimrdm10 = new BTSingleElimrdm10();
		CompetitionManager compete = new CompetitionManager(btSingleElimrdm10);

		compete.runCompetition("teams.txt");

	}

	private class Node implements IBinaryTree {

		private Node parent;
		private Node left;
		private Node right;
		private String team;
		private int score;

		public Node(Node parent, Node left, Node right, String team) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.team = team;
			score = 0;

		}

		public Node getParent() {
			return parent;
		}

		public void setParent(Node parent) {
			this.parent = parent;
		}

		public String getTeam() {
			return team;
		}

		public void setTeam(String team) {
			this.team = team;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public void setRight(Node right) {
			this.right = right;
		}

		public void setScore(int score) {
			this.score = score;
		}

		public Node() {

		}

		
		public IBinaryTree getLeft() {

			return left;
		}

		
		public IBinaryTree getRight() {
			
			return right;
		}

		
		public String getPlayer() {
			return team;
		}

		
		public int getScore() {
			
			return score;
		}

	}

	
	public void setPlayers(ArrayList<String> players) {
		ArrayList<Node> holdNodes = new ArrayList<Node>();
		pos = players.size() - 1;
		int size = players.size();
		int nodesNum = 0;
		for (int i = 0; i < size; i++) {
			Node temp = new Node();
			temp.setTeam(players.get(i));
			holdNodes.add(temp);
		}
		root = new Node();

		current = root;

		int i = 0;
		int count = 1;
		while (nodesNum != size) {

			nodesNum = nodesNum + 2;
			Node temp = new Node();
			temp.left = holdNodes.get(i);
			temp.left.setParent(temp);
			temp.right = holdNodes.get(i + 1);
			temp.right.setParent(temp);
			holdNodes.add(temp);
			i = i + 2;

			double holdLog;

			holdLog = Math.log10(nodesNum) / Math.log10(2);
			int remaining = size - nodesNum;

			if (nodesNum > count) {
				count = count * 2;
			}

			if (holdLog % 1 == 0 && remaining < count && remaining != 0) {

				int swap = holdNodes.size() - players.size() - 1;
				int holdSwap = swap;
				System.out.println(swap);
				int count2 = 0;
				// false = left;
				// true = right;
				Boolean leftOrRight = false;
				while (nodesNum != size) {
					// System.out.println(holdNodes.get(players.size()+3).right.team);

					nodesNum++;

					if (leftOrRight) {
						swap = count2;
						count2++;
						Node temp2 = new Node();
						temp2.right = holdNodes.get(players.size() + swap).right;
						// temp2.right = holdNodes.get().right;
						temp2.right.setParent(temp2);
						temp2.left = holdNodes.get(i);
						temp2.left.setParent(temp2);
						holdNodes.get(players.size() + swap).right = temp2;
						leftOrRight = false;
						System.out.println(swap);

						i++;
					} else {
						swap = holdSwap - count2;
						Node temp2 = new Node();
						temp2.left = holdNodes.get(players.size() + swap).left;
						temp2.left.setParent(temp2);
						temp2.right = holdNodes.get(i);
						temp2.right.setParent(temp2);
						holdNodes.get(players.size() + swap).left = temp2;
						System.out.println(swap);
						i++;
						leftOrRight = true;
					}

				}

			}
		}
		int j = 0;
		while (holdNodes.size() != players.size() + 1) {

			Node temp3 = new Node();
			temp3.right = holdNodes.get(players.size() + j);
			temp3.right.setParent(temp3);
			temp3.left = holdNodes.get(players.size() + j + 1);
			temp3.left.setParent(temp3);

			// holdNodes.get(players.size()+j).equals(temp3);
			holdNodes.set(players.size() + j, temp3);

			holdNodes.remove(players.size() + j + 1);

			j++;
			if (players.size() + j == holdNodes.size()) {
				j = 0;
			}
		}
		root = holdNodes.get(holdNodes.size() - 1);

	}

	// System.out.println(Math.log10(7)/Math.log10(2));

	
	public boolean hasNextMatch() {
		if (firstTime) {
			firstTime = false;
			return true;
		}
		if (st.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	
	public Match nextMatch() throws NoNextMatchException {

		if (!queueAndStackSet) {
			queueAndStackSet = true;
			getMatches();

		}

		Match next = new Match(st.peek().left.team, st.peek().right.team);

		return next;
	}

	public void getMatches() {
		current = root;
		queue.add(root);
		do {

			if (current.left.team == null) {
				queue.add(current.left);
			}
			if (current.right.team == null) {
				queue.add(current.right);
			}
			st.push(current);

			queue.poll();
			current = queue.peek();

		} while (!queue.isEmpty());

	}

	
	public String getPosition(int n) {

		for (int i = position.size() - 1; i >= 0; i--) {
			System.out.println(position.get(i).pos);
			System.out.println(position.get(i).team);
			if (position.get(i).pos == n) {
				return position.get(i).team;
			}
		}
		return "no team found";
	}

	private class posit {
		String team;
		int pos;

		public String getTeam() {
			return team;
		}

		public void setTeam(String team) {
			this.team = team;
		}

		public int getPos() {
			return pos;
		}

		public void setPos(int pos) {
			this.pos = pos;
		}

		public posit(String team, int pos) {
			this.team = team;
			this.pos = pos;
		}

		public posit() {
		}

	}

	
	public IBinaryTree getCompetitionTree() {
		return root;
	}

	
	public void setMatchScore(int p1, int p2) {
		st.peek().left.score = p1;
		st.peek().right.score = p2;
		posit hold = new posit();
		if (p1 > p2) {
			st.peek().team = st.peek().left.team;
			hold.setPos(pos);
			hold.setTeam(st.peek().right.team);
			position.add(hold);
		} else {
			st.peek().team = st.peek().right.team;
			hold.setPos(pos);
			hold.setTeam(st.peek().left.team);
			position.add(hold);
		}
		pos--;
		st.pop();
		if (st.isEmpty()) {
			hold.setPos(0);
			hold.setTeam(root.team);
			position.add(hold);
		}
		

	}
}
