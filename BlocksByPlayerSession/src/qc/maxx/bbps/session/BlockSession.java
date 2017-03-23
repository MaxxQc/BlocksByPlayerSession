package qc.maxx.bbps.session;

import org.bukkit.Location;
import org.bukkit.Material;

import qc.maxx.bbps.util.Util;

public class BlockSession {
	private Location location;
	private int materialId;
	private byte data;
	private String line1, line2, line3, line4;

	@SuppressWarnings("deprecation")
	public BlockSession(Location blockLocation, Material mat) {
		setBlockLocation(blockLocation);
		setMaterialId(mat.getId());
	}

	@SuppressWarnings("deprecation")
	public BlockSession(Location blockLocation, Material mat, byte data) {
		setBlockLocation(blockLocation);
		setMaterialId(mat.getId());
		setData(data);
	}

	@SuppressWarnings("deprecation")
	public BlockSession(Location blockLocation, Material mat, byte data, String[] lines) {
		setBlockLocation(blockLocation);
		setMaterialId(mat.getId());
		setData(data);
		setLine1(lines[0]);
		setLine2(lines[1]);
		setLine3(lines[2]);
		setLine4(lines[3]);
	}

	public Location getLocation() {
		return location;
	}

	public void setBlockLocation(Location location) {
		this.location = Util.correctLocation(location);
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int matId) {
		this.materialId = matId;
	}

	public byte getData() {
		return data;
	}

	public void setData(byte data) {
		this.data = data;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getLine4() {
		return line4;
	}

	public void setLine4(String line4) {
		this.line4 = line4;
	}
}