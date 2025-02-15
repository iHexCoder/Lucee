package lucee.runtime.functions.other;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import lucee.commons.lang.StringUtil;
import lucee.runtime.PageContext;
import lucee.runtime.exp.FunctionException;
import lucee.runtime.exp.PageException;
import lucee.runtime.ext.function.BIF;
import lucee.runtime.op.Caster;

public class Argon2CheckHash extends BIF {
	private static final long serialVersionUID = 4730626229333277363L;

	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		if (args.length != 2) {
			throw new FunctionException(pc, "Argon2CheckHash", 3, 3, args.length);
		}
		return call(pc, Caster.toString(args[0]), Caster.toString(args[1]));
	}

	public static boolean call(PageContext pc, String input, String hash) throws PageException {
		Argon2Types type;
		String variant = getVariant(pc, hash);
		if (StringUtil.isEmpty(variant, true)) throw new FunctionException(pc, "GenerateArgon2Hash", 1, "variant", "The Variant should be ARGON2i or ARGON2d or ARGON2id");
		variant = variant.trim();
		switch (variant.toLowerCase()) {
		case "argon2i":
			type = Argon2Types.ARGON2i;
			break;
		case "argon2d":
			type = Argon2Types.ARGON2d;
			break;
		case "argon2id":
			type = Argon2Types.ARGON2id;
			break;
		default:
			throw new FunctionException(pc, "Argon2CheckHash", 1, "variant", "The Variant should be ARGON2i or ARGON2d or ARGON2id");
		}
		Argon2 argon2 = Argon2Factory.create(type);
		char[] carrInput = input == null ? new char[0] : input.toCharArray();
		return argon2.verify(hash, carrInput);
	}

	private static String getVariant(PageContext pc, String hash) throws FunctionException {
		StringBuilder variant = new StringBuilder();
		for (int i = 0, n = hash.length(); i < n; i++) {
			char c = hash.charAt(i);
			if (i == 0 && c != '$') {
				throw new FunctionException(pc, "Argon2CheckHash", 1, "variant", "The format of hash string is wrong");
			}
			if (i > 0 && c == '$') {
				return variant.toString();
			}
			if (i > 0) {
				variant.append(c);
			}
		}
		throw new FunctionException(pc, "Argon2CheckHash", 1, "variant", "The format of hash string is wrong");
	}
}