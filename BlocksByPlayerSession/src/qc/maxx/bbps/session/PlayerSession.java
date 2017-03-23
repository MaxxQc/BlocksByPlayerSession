package qc.maxx.bbps.session;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class PlayerSession {
	private String playerUUID;
	private List<BlockSession> placedBlocks = new ArrayList();
	private List<BlockSession> changedBlocks = new ArrayList();
	private int numberOfPlacedBlocks;
	private int numberOfChangedBlocks;

	public PlayerSession(String playerUUID) {
		this.setPlayerUUID(playerUUID);
		this.setNumberOfPlacedBlocks(0);
		this.setNumberOfChangedBlocks(0);
	}

	public String getPlayerUUID() {
		return playerUUID;
	}

	public void setPlayerUUID(String playerUUID) {
		this.playerUUID = playerUUID;
	}

	public int getNumberOfPlacedBlocks() {
		return numberOfPlacedBlocks;
	}

	public void setNumberOfPlacedBlocks(int numberOfPlacedBlocks) {
		this.numberOfPlacedBlocks = numberOfPlacedBlocks;
	}

	public void addOneToNumberOfPlacedBlocks() {
		this.numberOfPlacedBlocks = numberOfPlacedBlocks + 1;
	}

	public int getNumberOfChangedBlocks() {
		return numberOfChangedBlocks;
	}

	public void setNumberOfChangedBlocks(int numberOfChangedBlocks) {
		this.numberOfChangedBlocks = numberOfChangedBlocks;
	}

	public void addOneToNumberOfChangedBlocks() {
		this.numberOfChangedBlocks = numberOfChangedBlocks + 1;
	}

	public List<BlockSession> getPlacedBlocks() {
		return placedBlocks;
	}

	public void addPlacedBlock(BlockSession placedBlock) {
		this.placedBlocks.add(placedBlock);
	}

	public void addPlacedBlocks(BlockSession... placedBlocks) {
		for (BlockSession block : placedBlocks)
			this.placedBlocks.add(block);
	}

	public List<BlockSession> getChangedBlocks() {
		return changedBlocks;
	}

	public void addChangedBlock(BlockSession changedBlock) {
		this.changedBlocks.add(changedBlock);
	}

	public void addChangedBlocks(BlockSession... changedBlocks) {
		for (BlockSession block : changedBlocks)
			this.changedBlocks.add(block);
	}
}